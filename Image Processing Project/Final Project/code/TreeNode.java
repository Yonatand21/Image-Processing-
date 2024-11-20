/**
 * Represents a node in a quadtree.
 *
 * @param <T> the type of value stored in the node
 */
public class TreeNode<T extends Number>
{
    /** The north-west child node. */
    public TreeNode<T> NW;
    /** The north-east child node. */
    public TreeNode<T> NE;
    /** The south-east child node. */
    public TreeNode<T> SE;
    /** The south-west child node. */
    public TreeNode<T> SW;
    /** The value stored in the node. */
    public T value;

    /**
     * Checks if the node is a leaf node (has no children).
     *
     * @return true if the node is a leaf, false otherwise
     */
    public boolean isLeaf()
    {
        return NW == null && NE == null && SE == null && SW == null;
    }

    /**
     * Returns a string representation of the node.
     *
     * @return a string representation of the node
     */
    @Override
    public String toString()
    {
        if (value == null) {
            return "null";
        }
        return value.toString();
    }
}
