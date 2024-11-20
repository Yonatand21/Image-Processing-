import java.util.Iterator;

/**
 * Represents a QuadTree data structure for storing and manipulating images.
 *
 * @param <Pixel> the type of pixel values stored in the QuadTreeImage
 */
public class QuadTreeImage<Pixel extends Number> implements Comparable<QuadTreeImage<Pixel>>, Iterable<TreeNode<Pixel>>
{
    /**
     * The root node of the QuadTree.
     */

    private TreeNode<Pixel> root;

    /**
     * The width of the image.
     */
    private int imageWidth;

    /**
     * Gets the root node of the QuadTree.
     *
     * @return the root node
     */
    public TreeNode<Pixel> getRoot()
    {
        return root;
    }

    /**
     * Gets the size of the image (width or height).
     *
     * @return the size of the image
     */
    public int getSize()
    {
        return imageWidth;
    }

    /**
     * Constructs a QuadTreeImage from a 2D array of pixel values.
     *
     * @param array the 2D array of pixel values
     */
    public QuadTreeImage(Pixel[][] array)
    {
        if (array == null || array.length == 0 || array[0].length == 0) {
            throw new IllegalArgumentException("Invalid input array");
        }

        imageWidth = array.length;

        root = buildQuadTree(array, 0, 0, imageWidth);
    }
    /**
     * Builds a QuadTree node recursively from a 2D array of pixel values.
     * If the size of the sub-array is 1, a leaf node is created with the pixel value.
     * Otherwise, the sub-array is divided into quadrants, and each quadrant is used to
     * recursively build the QuadTree node for that quadrant. If all quadrants end up
     * being leaf nodes with the same pixel value, the node is collapsed into a single
     * leaf node with that value.
     *
     * @param array the 2D array of pixel values
     * @param x the starting x-coordinate of the sub-array
     * @param y the starting y-coordinate of the sub-array
     * @param size the size of the sub-array
     * @return the root node of the QuadTree representing the sub-array
     */
    private TreeNode<Pixel> buildQuadTree(Pixel[][] array, int x, int y, int size) {
        if (size == 1) {
            TreeNode<Pixel> leaf = new TreeNode<>();
            leaf.value = array[x][y];
            return leaf;
        }

        int newSize = size / 2;

        TreeNode<Pixel> node = new TreeNode<>();
        node.NW = buildQuadTree(array, x, y, newSize);
        node.NE = buildQuadTree(array, x, y + newSize, newSize);
        node.SE = buildQuadTree(array, x + newSize, y + newSize, newSize);
        node.SW = buildQuadTree(array, x + newSize, y, newSize);

        if (node.NW.isLeaf() && node.NE.isLeaf() && node.SE.isLeaf() && node.SW.isLeaf() &&
                node.NW.value.equals(node.NE.value) && node.NW.value.equals(node.SE.value) && node.NW.value.equals(node.SW.value)) {
            node.value = node.NW.value;
            node.NW = node.NE = node.SE = node.SW = null;
        }

        return node;
    }

    /**
     * Gets the color of the pixel at the specified coordinates.
     *
     * @param w the x-coordinate of the pixel
     * @param h the y-coordinate of the pixel
     * @return the color of the pixel
     */
    public Pixel getColor(int w, int h)
    {
        return getColor(root, 0, 0, imageWidth, w, h);
    }
    /**
     * Gets the color of the pixel at the specified coordinates in the QuadTree.
     * If the node is a leaf node, returns the color value of the leaf node.
     * Otherwise, recursively searches for the appropriate child node that contains
     * the specified coordinates and retrieves the color value from that node.
     *
     * @param node the current node being examined
     * @param x the starting x-coordinate of the current node
     * @param y the starting y-coordinate of the current node
     * @param size the size of the current node
     * @param w the x-coordinate of the pixel to retrieve
     * @param h the y-coordinate of the pixel to retrieve
     * @return the color of the pixel at the specified coordinates
     */
    private Pixel getColor(TreeNode<Pixel> node, int x, int y, int size, int w, int h)
    {
        if (node.isLeaf()) {
            return node.value;
        }

        int newSize = size / 2;
        if (w < x + newSize && h < y + newSize) {
            return getColor(node.NW, x, y, newSize, w, h);
        } else if (w < x + newSize && h >= y + newSize) {
            return getColor(node.SW, x, y + newSize, newSize, w, h);
        } else if (w >= x + newSize && h < y + newSize) {
            return getColor(node.NE, x + newSize, y, newSize, w, h);
        } else {
            return getColor(node.SE, x + newSize, y + newSize, newSize, w, h);
        }
    }
    /**
     * Sets the color of the pixel at the specified coordinates.
     *
     * @param w the x-coordinate of the pixel
     * @param h the y-coordinate of the pixel
     * @param v the new color value
     */
    public void setColor(int w, int h, Pixel v)
    {
        setColor(root, 0, 0, imageWidth, w, h, v);
    }
    /**
     * Sets the color of the pixel at the specified coordinates in the QuadTree.
     * If the node is a leaf node, sets the color value of the leaf node to the specified value.
     * Otherwise, recursively searches for the appropriate child node that contains
     * the specified coordinates and sets the color value of that node to the specified value.
     * After setting the color, checks if the node can be collapsed into a leaf node
     * by comparing the colors of its children and collapsing if they are all the same.
     *
     * @param node the current node being examined
     * @param x the starting x-coordinate of the current node
     * @param y the starting y-coordinate of the current node
     * @param size the size of the current node
     * @param w the x-coordinate of the pixel to set
     * @param h the y-coordinate of the pixel to set
     * @param v the new color value to set
     */
    private void setColor(TreeNode<Pixel> node, int x, int y, int size, int w, int h, Pixel v)
    {
        if (node.isLeaf()) {
            node.value = v;
            return;
        }

        int newSize = size / 2;
        if (w < x + newSize && h < y + newSize) {
            setColor(node.NW, x, y, newSize, w, h, v);
        } else if (w < x + newSize && h >= y + newSize) {
            setColor(node.SW, x, y + newSize, newSize, w, h, v);
        } else if (w >= x + newSize && h < y + newSize) {
            setColor(node.NE, x + newSize, y, newSize, w, h, v);
        } else {
            setColor(node.SE, x + newSize, y + newSize, newSize, w, h, v);
        }

        // Check if the node needs to be collapsed into a leaf
        if (node.NW.isLeaf() && node.NE.isLeaf() && node.SE.isLeaf() && node.SW.isLeaf() &&
                node.NW.value.equals(node.NE.value) && node.NW.value.equals(node.SE.value) && node.NW.value.equals(node.SW.value)) {
            node.value = node.NW.value;
            node.NW = node.NE = node.SE = node.SW = null;
        }
    }
    /**
     * Counts the total number of nodes in the QuadTree.
     *
     * @return the total number of nodes
     */
    public int countNodes()
    {
        return countNodes(root);
    }
    /**
     * Recursively counts the total number of nodes in the subtree rooted at the given node.
     *
     * @param node the root of the subtree
     * @return the total number of nodes in the subtree
     */
    private int countNodes(TreeNode<Pixel> node)
    {
        if (node == null) {
            return 0;
        }

        return 1 + countNodes(node.NW) + countNodes(node.NE) + countNodes(node.SE) + countNodes(node.SW);
    }
    /**
     * Returns a string representation of the QuadTreeImage, showing the structure of the QuadTree and the pixel values at each leaf node.
     *
     * @return a string representation of the QuadTreeImage
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        toStringHelper(root, 0, 0, imageWidth, result);
        // Remove the trailing comma
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        return result.toString();
    }
    /**
     * Recursively builds a string representation of the QuadTreeImage, showing the structure of the QuadTree and the pixel values at each leaf node.
     *
     * @param node   the current node being processed
     * @param x      the x-coordinate of the current node
     * @param y      the y-coordinate of the current node
     * @param size   the size of the current node
     * @param result a StringBuilder to append the string representation to
     */
    private void toStringHelper(TreeNode<Pixel> node, int x, int y, int size, StringBuilder result) {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            result.append(String.format("{%d %d %d %d},", x, y, size, node.value));
        } else {
            int newSize = size / 2;
            toStringHelper(node.NW, x, y, newSize, result);
            toStringHelper(node.NE, x + newSize, y, newSize, result);
            toStringHelper(node.SE, x + newSize, y + newSize, newSize, result);
            toStringHelper(node.SW, x, y + newSize, newSize, result);
        }
    }

    /**
     * Computes the total brightness of the QuadTreeImage.
     *
     * @return the total brightness
     */
    public int brightness() {
        return brightnessHelper(root, imageWidth);
    }
    /**
     * Recursively calculates the total brightness of the QuadTreeImage, which is the sum of the pixel values multiplied by the square of the size of each leaf node.
     *
     * @param node the current node being processed
     * @param size the size of the current node
     * @return the total brightness of the QuadTreeImage
     */
    private int brightnessHelper(TreeNode<Pixel> node, int size) {
        if (node == null) {
            return 0;
        }

        if (node.isLeaf()) {
            return node.value.intValue() * size * size;
        } else {
            int newSize = size / 2;
            return brightnessHelper(node.NW, newSize) + brightnessHelper(node.NE, newSize) +
                    brightnessHelper(node.SE, newSize) + brightnessHelper(node.SW, newSize);
        }
    }
    /**
     * Appends a string representation of the subtree rooted at the given node to the StringBuilder.
     *
     * @param node the root of the subtree
     * @param sb   the StringBuilder to which the string representation is appended
     */
    private void toString(TreeNode<Pixel> node, StringBuilder sb)
    {
        if (node == null) {
            return;
        }

        if (node.isLeaf()) {
            sb.append("{").append(node.value).append("}");
        } else {
            sb.append("{");
            toString(node.NW, sb);
            toString(node.NE, sb);
            toString(node.SE, sb);
            toString(node.SW, sb);
            sb.append("}");
        }
    }
    /**
     * Returns an iterator over the nodes of the QuadTreeImage in a breadth-first manner.
     *
     * @return an iterator over the nodes of the QuadTreeImage
     */
    @Override
    public Iterator<TreeNode<Pixel>> iterator()
    {
        return new QuadTreeImageIterator<>(this);
    }

    /**
     * Iterator for iterating over the nodes of a QuadTreeImage in a breadth-first manner.
     *
     * @param <Pixel> the type of pixel values stored in the QuadTreeImage
     */
    public class QuadTreeImageIterator<Pixel extends Number> implements Iterator<TreeNode<Pixel>>
    {
        /**
         * Queue used for breadth-first traversal of the QuadTree nodes during iteration.
         */
        private Queue<TreeNode<Pixel>> queue;
        /**
         * Constructs a QuadTreeImageIterator for iterating over the nodes of a QuadTreeImage in a breadth-first manner.
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
         * Returns the next node in the iteration.
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

    /**
     * Compares the brightness of this QuadTreeImage to another QuadTreeImage.
     *
     * @param other the other QuadTreeImage to compare to
     * @return the difference in brightness between the two images
     */
    public int compareTo(QuadTreeImage<Pixel> other)
    {
        return brightness() - other.brightness();
    }
}
