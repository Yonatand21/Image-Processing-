import java.util.Iterator;

/**
 * Iterator for iterating over the nodes of a QuadTreeImage in a breadth-first manner.
 *
 * @param <Pixel> the type of pixel values stored in the QuadTreeImage
 */
public class QuadTreeImageIterator<Pixel extends Number> implements Iterator<TreeNode<Pixel>>
{
    /**
     * The queue used for breadth-first iteration.
     */
    private Queue<TreeNode<Pixel>> queue;

    /**
     * Constructs a QuadTreeImageIterator for the given QuadTreeImage.
     *
     * @param image the QuadTreeImage to iterate over
     */
    public QuadTreeImageIterator(QuadTreeImage<Pixel> image)
    {
        queue = new Queue<>();
        if (image.getRoot() != null) {
            queue.enqueue(image.getRoot());
        }
    }

    /**
     * Checks if there are more nodes to iterate over.
     *
     * @return true if there are more nodes, false otherwise
     */
    public boolean hasNext()
    {
        return !queue.isEmpty();
    }

    /**
     * Retrieves the next node in the iteration.
     *
     * @return the next node
     * @throws RuntimeException if there are no more elements in the iterator
     */
    public TreeNode<Pixel> next()
    {
        if (!hasNext()) {
            throw new RuntimeException("No more elements in the iterator");
        }
        TreeNode<Pixel> node = queue.dequeue();
        if (node.NW != null) queue.enqueue(node.NW);
        if (node.NE != null) queue.enqueue(node.NE);
        if (node.SE != null) queue.enqueue(node.SE);
        if (node.SW != null) queue.enqueue(node.SW);
        return node;
    }
}
