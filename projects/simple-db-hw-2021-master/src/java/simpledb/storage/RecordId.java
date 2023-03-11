package simpledb.storage;

import java.io.Serializable;

/**
 * A RecordId is a reference to a specific tuple on a specific page of a
 * specific table.
 */
public class RecordId implements Serializable {

    private static final long serialVersionUID = 1L;


    private PageId pageId;
    private int tupleno;
    /**
     * Creates a new RecordId referring to the specified PageId and tuple
     * number.
     * 
     * @param pid
     *            the pageid of the page on which the tuple resides
     * @param tupleno
     *            the tuple number within the page.
     */
    public RecordId(PageId pid, int tupleno) {
        // some code goes here
        this.pageId = pid;
        this.tupleno = tupleno;
    }

    /**
     * @return the tuple number this RecordId references.
     */
    public int getTupleNumber() {
        // some code goes here
        return this.tupleno;
    }

    /**
     * @return the page id this RecordId references.
     */
    public PageId getPageId() {
        // some code goes here
        return this.pageId;
    }

    /**
     * Two RecordId objects are considered equal if they represent the same
     * tuple.
     * 如果两个recordId指向的tuple是相同的，就认为他们是相同的
     *
     * 只要页号和行号相同，那么两个tuple就是相同的
     * @return True if this and o represent the same tuple
     *
     */
    @Override
    public boolean equals(Object o) {
        // some code goes here
        if (!(o instanceof RecordId)) return false;
        RecordId other = (RecordId)o;
        return this.tupleno == other.tupleno && this.pageId.equals(other.pageId);
    }

    /**
     * You should implement the hashCode() so that two equal RecordId instances
     * (with respect to equals()) have the same hashCode().
     * 
     * @return An int that is the same for equal RecordId objects.
     */
    @Override
    public int hashCode() {
        // some code goes here
        String hash = "" + this.pageId.getTableId() + " " + this.pageId.getPageNumber() + " " + tupleno;    // 表 + 页 + tuple号可以唯一确定一条记录
//        throw new UnsupportedOperationException("implement this");
        return hash.hashCode();

    }

}
