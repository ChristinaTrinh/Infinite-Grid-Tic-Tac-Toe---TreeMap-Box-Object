public class Box implements Comparable<Box>
{
    private int row;
    private int column;

    public Box()
    {
        this(0,0);
    }

    public Box(int row, int column)
    {
        setLocation(row, column);
    }

    public Box(Box other)
    {
        setLocation(other.row, other.column);
    }

    public void setLocation(int row, int column)
    {
        this.row = row;
        this.column = column;
    }

    public int getRow()
    {
        return this.row;
    }

    public int getColumn()
    {
        return this.column;
    }

    public String toString()
    {
        return "row = " + this.row + ", column = " + this.column;
    }
    public int compareTo(Box b2)
    {
        if(this.getRow() < b2.getRow())
            return -1;
        else if((this.getRow() == b2.getRow()) && (this.getColumn() < b2.getColumn()))
            return -1;
        else if((this.getRow() == b2.getRow()) && (this.getColumn() == b2.getColumn()))
            return 0;
        else
            return 1;
    }
}