package simpledb.storage;

import simpledb.common.Database;
import simpledb.common.DbException;
import simpledb.common.Debug;
import simpledb.common.Catalog;
import simpledb.transaction.Transaction;
import simpledb.transaction.TransactionId;

import java.util.*;
import java.io.*;

/**
 * Each instance of HeapPage stores data for one page of HeapFiles and
 * implements the Page interface that is used by BufferPool.
 *
 * @see HeapFile
 * @see BufferPool
 *
 */
public class HeapPage implements Page {


    private final HeapPageId pid;
    private final TupleDesc td;
    private final byte[] header;                                    // 头部的bitmap，每一个元组占用一位
    private final Tuple[] tuples;
//    final int numSlots;

    private TransactionId tid;                   // 事务id
    private boolean dirty;                      // 是否是脏页

    byte[] oldData;
    private final Byte oldDataLock= (byte) 0;

    /**
     * Create a HeapPage from a set of bytes of data read from disk.
     * The format of a HeapPage is a set of header bytes indicating
     * the slots of the page that are in use, some number of tuple slots.
     *  Specifically, the number of tuples is equal to: <p>
     *          floor((BufferPool.getPageSize()*8) / (tuple size * 8 + 1))
     * <p> where tuple size is the size of tuples in this
     * database table, which can be determined via {@link Catalog#getTupleDesc}.
     * The number of 8-bit header words is equal to:
     * <p>
     *      ceiling(no. tuple slots / 8)
     * <p>
     * @see Database#getCatalog
     * @see Catalog#getTupleDesc
     * @see BufferPool#getPageSize()
     */
    public HeapPage(HeapPageId id, byte[] data) throws IOException {
        this.pid = id;
        this.td = Database.getCatalog().getTupleDesc(id.getTableId());
//        this.numSlots = getNumTuples();
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));

        // allocate and read the header slots of this page
        // 获取头部信息。
        header = new byte[getHeaderSize()];
        for (int i=0; i< header.length; i++)
            header[i] = dis.readByte();
        // 获取元组

//        System.out.println(String.format("一页可以存储 %d 条信息\n", getNumTuples()));
        tuples = new Tuple[getNumTuples()];
        try{
            // allocate and read the actual records of this page
            for (int i=0; i<tuples.length; i++)
                tuples[i] = readNextTuple(dis,i);
        }catch(NoSuchElementException e){
            e.printStackTrace();
        }
        dis.close();

        setBeforeImage();
    }

    /** Retrieve the number of tuples on this page.
        @return the number of tuples on this page
     返回一共有多少元组
     使用页面的大小 * 8 / (元组的大小 * 8 + 1)
     需要一个脏位来记录是否使用过
     如果是lru，还需要一个lru位记录使用的次数
    */
    private int getNumTuples() {
        // some code goes here

        return BufferPool.getPageSize() * 8 / (td.getSize() * 8 + 1);

    }

    /**
     *
     * 获取头部长度
     * 每一个元组所占用的字节数
     * Computes the number of bytes in the header of a page in a HeapFile with each tuple occupying tupleSize bytes
     * @return the number of bytes in the header of a page in a HeapFile with each tuple occupying tupleSize bytes
     */
    private int getHeaderSize() {

        // some code goes here
        return (getNumTuples() + 8 - 1) / 8;            // 上取整，每一行占用一位，然后计算byte数组大小，所以需要 / 8

    }

    /** Return a view of this page before it was modified
        -- used by recovery */
    public HeapPage getBeforeImage(){
        try {
            byte[] oldDataRef = null;
            synchronized(oldDataLock)
            {
                oldDataRef = oldData;
            }
            return new HeapPage(pid,oldDataRef);
        } catch (IOException e) {
            e.printStackTrace();
            //should never happen -- we parsed it OK before!
            System.exit(1);
        }
        return null;
    }

    /**
     *
     * 保存脏页的快照，相当于redo日志。
     * 定时将这个redo日志写入磁盘就行了。
     * 每次重启的时候，将redo日志的内容进行重启
     * */
    public void setBeforeImage() {
        synchronized(oldDataLock)
        {
            oldData = getPageData().clone();
        }
    }

    /**
     * @return the PageId associated with this page.
     */
    public HeapPageId getId() {
    // some code goes here
        return this.pid;
//    throw new UnsupportedOperationException("implement this");
    }

    /**
     * Suck up tuples from the source file.
     */
    private Tuple readNextTuple(DataInputStream dis, int slotId) throws NoSuchElementException {
        // if associated bit is not set, read forward to the next tuple, and
        // return null.
        if (!isSlotUsed(slotId)) {
            for (int i=0; i<td.getSize(); i++) {
                try {
                    dis.readByte();
                } catch (IOException e) {
                    throw new NoSuchElementException("error reading empty tuple");
                }
            }
            return null;
        }

        // read fields in the tuple
        Tuple t = new Tuple(td);
        RecordId rid = new RecordId(pid, slotId);
        t.setRecordId(rid);
        try {
            for (int j=0; j<td.numFields(); j++) {
                Field f = td.getFieldType(j).parse(dis);
                t.setField(j, f);
            }
        } catch (java.text.ParseException e) {
            e.printStackTrace();
            throw new NoSuchElementException("parsing error!");
        }

        return t;
    }

    /**
     * Generates a byte array representing the contents of this page.
     * Used to serialize this page to disk.
     * <p>
     * The invariant here is that it should be possible to pass the byte
     * array generated by getPageData to the HeapPage constructor and
     * have it produce an identical HeapPage object.
     *
     * @see #HeapPage
     * @return A byte array correspond to the bytes of this page.
     */
    public byte[] getPageData() {
        int len = BufferPool.getPageSize();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(len);
        DataOutputStream dos = new DataOutputStream(baos);

        // create the header of the page
        for (byte b : header) {
            try {
                dos.writeByte(b);
            } catch (IOException e) {
                // this really shouldn't happen
                e.printStackTrace();
            }
        }

        // create the tuples
        for (int i=0; i<tuples.length; i++) {

            // empty slot
            if (!isSlotUsed(i)) {
                for (int j=0; j<td.getSize(); j++) {
                    try {
                        dos.writeByte(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                continue;
            }

            // non-empty slot
            for (int j=0; j<td.numFields(); j++) {
                Field f = tuples[i].getField(j);
                try {
                    f.serialize(dos);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // padding
        int zerolen = BufferPool.getPageSize() - (header.length + td.getSize() * tuples.length); //- numSlots * td.getSize();
        byte[] zeroes = new byte[zerolen];
        try {
            dos.write(zeroes, 0, zerolen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
    }

    /**
     * Static method to generate a byte array corresponding to an empty
     * HeapPage.
     * Used to add new, empty pages to the file. Passing the results of
     * this method to the HeapPage constructor will create a HeapPage with
     * no valid tuples in it.
     *
     * @return The returned ByteArray.
     */
    public static byte[] createEmptyPageData() {
        int len = BufferPool.getPageSize();
        return new byte[len]; //all 0
    }

    /**
     * Delete the specified tuple from the page; the corresponding header bit should be updated to reflect
     *   that it is no longer stored on any page.
     * @throws DbException if this tuple is not on this page, or tuple slot is
     *         already empty.
     * @param t The tuple to delete
     */
    public void deleteTuple(Tuple t) throws DbException {
        // some code goes here
        // not necessary for lab1
        // 1. 删除元组
        // 2. 对应的header中的bitmap需要更新
        // 0. 如果不存在或者说slot已经空了，抛出异常
//        int idx = 0, flag = 0;
//        for (Tuple tup : tuples) {
//            if (isSlotUsed(idx) && tup.equals(t)) {         // 如果没有被删除，并且是需要删除的元组
//                flag = 1;
//                markSlotUsed(idx, false);           // 清空标记
//            }
//            idx++;
//        }
//        if (flag == 0) throw new DbException("需要删除的元素不存在!");
//
        // 1. 首先找到对应的元组
        // 2. 如果不包含，或者已经空了，直接报错/
        // 置1，然后删除，不用懒删除，不是磁盘，垃圾回收很快，大小却很重要。
        // 判断tuple相同：表id相同，页号相同
        int tupleId = t.getRecordId().getTupleNumber();
        if (tuples[tupleId] == null || !t.getRecordId().getPageId().equals(pid)) {
            throw new DbException("页面中不包含该tuple");
        }
        if (!isSlotUsed(tupleId)) throw new DbException("tuple solt已经空了!");
        markSlotUsed(tupleId, false);
        tuples[tupleId] = null;     // 删除内容
    }

    /**
     * Adds the specified tuple to the page;  the tuple should be updated to reflect
     *  that it is now stored on this page.
     * @throws DbException if the page is full (no empty slots) or tupledesc
     *         is mismatch.
     * @param t The tuple to add.
     */
    public void insertTuple(Tuple t) throws DbException {
        // some code goes here
        // not necessary for lab1
        // 1. 找到第一个可以使用的
        // 2. 插入，然后更新标记
        // 3. 如果已经满了，直接抛出异常
        // 4. 插入后break

        // 元组所在的行号更新
        if (getNumEmptySlots() == 0) throw new DbException("本页已经满了，无法插入本页!");
        for (int i = 0; i < getNumTuples(); i++) {
            if (!isSlotUsed(i)) {
                markSlotUsed(i, true);
                t.setRecordId(new RecordId(pid, i));
                tuples[i] = t;
                return ;
            }
        }
    }

    /**
     * Marks this page as dirty/not dirty and record that transaction
     * that did the dirtying
     */
    public void markDirty(boolean dirty, TransactionId tid) {
        // some code goes here
	// not necessary for lab1
        // 标记脏页和对应的事务id
        this.tid = tid;
        this.dirty = dirty;
    }

    /**
     * Returns the tid of the transaction that last dirtied this page, or null if the page is not dirty
     * 如果已经是脏页了，返回事务的id，否则返回null
     *
     */
    public TransactionId isDirty() {
        // some code goes here
	// Not necessary for lab1
        if (dirty) return tid;
        else return null;
    }

    /**
     * Returns the number of empty slots on this page.
     * 总的slots - 已经使用的。
     * 遍历所有solts，找到没有使用的就行了。
     */
    public int getNumEmptySlots() {
        // some code goes here
//        int used = 0;
//        for (int line : header) {
//            for (int i = 0; i < 8; i++) {
//                used += line >> i & 1;
//            }
//        }
        int ans = 0;
        for (int i = 0; i < getNumTuples(); i++) {
           if (!isSlotUsed(i))
               ans++;
        }
        return ans;
    }

    /**
     * Returns true if associated slot on this page is filled.
     *
     */
    public boolean isSlotUsed(int i) {
        // some code goes here
        // 返回是否已经使用过了，直接看bitmat中的行和列是否已经使用过了
        int quot = i / 8;
        int mov = i % 8;
        return (header[quot] >> mov & 1) == 1;
    }

    /**
     * Abstraction to fill or clear a slot on this page.
     * 填充，或者去掉
     */
    private void markSlotUsed(int i, boolean value) {
        // some code goes here
        // not necessary for lab1
        if (isSlotUsed(i) && value) throw new IllegalArgumentException("已经被占用，无法使用");
        if (!isSlotUsed(i) && !value) throw new IllegalArgumentException("没有被使用，无法清除");
        if (value) header[i / 8] |= 1 << (i % 8);           // 当前位置1
        else header[i / 8] &= ~(1 << (i % 8));              // 当前为置为0
    }

    /**
     * @return an iterator over all tuples on this page (calling remove on this iterator throws an UnsupportedOperationException)
     * (note that this iterator shouldn't return tuples in empty slots!)
     * 返回一些东西
     */
    public Iterator<Tuple> iterator() {
        // some code goes here
        ArrayList<Tuple>list = new ArrayList<>();
        for (int i = 0; i < getNumTuples(); i++) {
            if (isSlotUsed(i))
                list.add(tuples[i]);
        }
        return list.iterator();
    }

}

