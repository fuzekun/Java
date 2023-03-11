package simpledb.storage;

import simpledb.common.Type;

import java.io.Serializable;
import java.util.*;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * 用来存储tuople的数组
     * */
    private final TDItem[] tdItems;
    /**
     * A help class to facilitate organizing the information of each field
     * 用于帮助组织域信息的类。
     * */
    public static class TDItem implements Serializable {



        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         * */
        public final Type fieldType;                            // 类型



        /**
         * The name of the field
         * */
        public final String fieldName;                            // 名称

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }
        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }
    }

    /**
     * @return
     *        An iterator which iterates over all the field TDItems
     *        that are included in this TupleDesc
     * */
    public Iterator<TDItem> iterator() {
        // some code goes here
        return (Iterator<TDItem>) Arrays.asList(tdItems).iterator();        // 将数组变成list，然后返回其迭代器
    }

    private static final long serialVersionUID = 1L;

    /*
    *
    *  自己定义的，方便SeqScan中创建
    *
    * */
    public TupleDesc(TDItem[] items) {
        this.tdItems = items;
    }
    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     * @param fieldAr
     *            array specifying the names of the fields. Note that names may
     *            be null.
     */

    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        // some code goes here
        // 初始化函数，放类型和属性名称
        int n = fieldAr.length;
        tdItems = new TDItem[n];
        for (int i = 0; i < n; i++) {
            tdItems[i] = new TDItem(typeAr[i], fieldAr[i]);
        }
//        tdItems = new TDItem[typeAr.length];
//        for (int i = 0; i < typeAr.length; i++) {
//            tdItems[i] = new TDItem(typeAr[i], fieldAr[i]);
//        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr
     *            array specifying the number of and types of fields in this
     *            TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // some code goes here
        tdItems = new TDItem[typeAr.length];
        for (int i = 0; i < typeAr.length; i++) {
            tdItems[i] = new TDItem(typeAr[i], "");
        }
    }


    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {
        // some code goes her
        return tdItems.length;
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i
     *            index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // 这里在测试的时候已经保证了i一定是小于index的。
//        assert (i > 0 && i <= tdItems.length);          // 断言是为了调试，相当于打断点。
        // some code goes here
        if (i < 0 || i >= tdItems.length) {
            throw  new ArrayIndexOutOfBoundsException("pos" + i + " is not a valid index");
        }
        return tdItems[i].fieldName;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i
     *            The index of the field to get the type of. It must be a valid
     *            index.
     * @return the type of the ith field
     * @throws NoSuchElementException
     *             if i is not a valid field reference.
     *
     *             返回某一个域的类型就行了
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        // some code goes here
        if (i < 0 || i >= tdItems.length) {
            throw  new NoSuchElementException("pos" + i + " is not a valid index");
        }
        return tdItems[i].fieldType;
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name
     *            name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException
     *             if no field with a matching name is found.
     *             给域，得到对应的id
     */
    public int fieldNameToIndex(String name) throws NoSuchElementException {
        // some code goes here
        int i = 0;
        for (TDItem item : this.tdItems) {
            if (item.fieldName.equals(name))
                return i;
            i ++;
        }
        throw new NoSuchElementException("not find fieldName:" + name);
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     *
     *          返回每一个tuple占用的字节的大小。
     *          直接求所有域的所占用字节的大小就行了。
     */
    public int getSize() {
        // some code goes here
        int n = tdItems.length, ans = 0;
        for (int i = 0; i < n; i++) {
            ans += tdItems[i].fieldType.getLen();
        }
        return ans;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     * 合并两个表描述。
     * 采用先表td1后表td2的方式。
     * @param td1
     *            The TupleDesc with the first fields of the new TupleDesc
     * @param td2
     *            The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        // some code goes here

        Type[] typeArr = new Type[td1.numFields() + td2.numFields()];
        String[] fieldArr = new String[td1.numFields() + td2.numFields()];
        int i = 0;
        for (TDItem item : td1.tdItems) {
            typeArr[i] = item.fieldType;
            fieldArr[i] = item.fieldName;
            i++;
        }
        for (TDItem item : td2.tdItems) {
            typeArr[i] = item.fieldType;
            fieldArr[i] = item.fieldName;
            i++;
        }
        return new TupleDesc(typeArr, fieldArr);
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they have the same number of items
     * and if the i-th type in this TupleDesc is equal to the i-th type in o
     * for every i.
     * 只要对应类型相同，那么两个tupleDesc就是相同的.
     *
     * @param o
     *            the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */

    public boolean equals(Object o) {
        // some code goes here
        if (!(o instanceof TupleDesc)) return false;                    // 首先保证是tupleDesc类型，其次判断两个对象是否相等
        TupleDesc other = (TupleDesc) o;
        if (this.numFields() != other.numFields()) return false;
        for (int i = 0; i < this.numFields(); i++) {
            if (!this.tdItems[i].fieldType.equals(other.tdItems[i].fieldType))
                return false;
        }
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() result
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     *返回形如  type1(name1), type2(name2)这种
     * @return String describing this descriptor.
     *
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = this.tdItems.length;
        for (int i = 0; i < n; i++) {
            sb.append(tdItems[i].fieldName);
            sb.append("(");
            sb.append(tdItems[i].fieldType);
            sb.append(")");
            if (i != n - 1) sb.append(",");
        }
        return sb.toString();
    }
}
