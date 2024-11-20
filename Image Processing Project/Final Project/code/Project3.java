public class Project3
{
    public static void main(String[] args)
    {
        // load an image from a file
        Short[][] array = Utilities.loadData("C:\\Users\\AZAN LAPTOP STORE\\Downloads\\talha image processing 6k\\VERY LAST VERSION\\final submission\\code\\image1.pgm");

        // construct the quadtree
        QuadTreeImage<Short> img1 = new QuadTreeImage<>(array);
        String imgg1 = "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{2 6 1 0},{2 7 1 255},{3 7 1 255},{3 6 1 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}";
        
        // invoke toString()
        System.out.println("Image representation\nExpected is\n" +
                "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{2 6 1 0},{2 7 1 255},{3 7 1 255},{3 6 1 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}" +
                "\nActual is\n" + imgg1);


        // invoke the iterator
        System.out.println("-----\nImage iterator\nExpected is\n" +
                "null 255 null null 0 255 255 null 0 0 255 null 255 0 255 255 255 255 255 0 255" +
                "\nActual is");
        for(TreeNode<Short> iter : img1)
            System.out.print(iter+" ");
        String imgg2 = "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}";

        // count the number of quadtree nodes; expected: 21
        System.out.println("\n-----\nNumber of nodes (expected is 21): " + img1.countNodes());

        // calculate brightness; expected: 9690
        System.out.println("-----\nBrightness (expected is 9690): " + img1.brightness());

        // load a second image from another file
        array = Utilities.loadData("C:\\Users\\AZAN LAPTOP STORE\\Downloads\\talha image processing 6k\\VERY LAST VERSION\\final submission\\code\\image2.pgm");

        // construct a second quadtree
        QuadTreeImage<Short> img2 = new QuadTreeImage<>(array);

        // compare the two images, i.e. which one is brighter; expected: -111
        System.out.println("-----\nBrightness comparison (expected is -111): " + img1.compareTo(img2));

        // change the color of a single pixel in image1
        img1.setColor(6, 2, Short.valueOf("255"));

        // save the modified image in a file
        Utilities.exportImage(img1,"C:\\Users\\AZAN LAPTOP STORE\\Downloads\\talha image processing 6k\\VERY LAST VERSION\\final submission\\code\\image1_after_setColor.pgm");
        // you must open the image to visually inspect the result

        // invoke the toString to see how the tree looks like after the modification
        System.out.println("-----\nImage representation after setColor\nExpected is\n" +
                "{0 0 4 255},{4 0 4 0},{0 4 2 255},{0 6 2 255},{2 6 2 255},{2 4 2 0},{4 4 2 0},{4 6 2 255},{6 4 2 255},{6 6 1 255},{6 7 1 255},{7 7 1 0},{7 6 1 255}" +
                "\nActual is\n" + imgg2);

        // inspect the values of all the nodes after the modification
        System.out.println("-----\nImage iterator after setColor\nExpected is\n" +
                "null 255 null null 0 255 255 255 0 0 255 null 255 255 255 0 255" +
                "\nActual is");
        for(TreeNode<Short> iter : img1)
            System.out.print(iter+" ");

        // construct a FIFO queue
        Queue<Integer> queue = new Queue<>();
        try
        {
            // it should throw an exception because queue is empty
            queue.dequeue();
            System.out.println("\n-----\nError: dequeue on an empty queue should throw an exception");
        }
        catch (Exception e)
        {
            System.out.println("\n-----\nThat's correct, the queue should throw an exception");
        }

        Integer r = null;

        for (int i=0; i<1000000; i++)
            queue.enqueue(i);
        for (int i=0; i<1000000; i++)
            r = queue.dequeue();

        if (queue.isEmpty())
            System.out.println("-----\nThat's correct, the queue is empty now");
        else
            System.out.println("-----\nError: the queue should be empty now");

        if (r==999999)
            System.out.println("-----\nLast dequeue is correct!");
        else
            System.out.println("-----\nError: last dequeue is incorrect!");

        System.out.println("\n\n******************** WARNING ******************\n                                              \n             THIS IS NOT A TESTER             \n THOROUGH TESTING IS NEEDED BEFORE SUBMISSION \n                                              \n*");






        // Inspect the values of all the nodes after the modification
        System.out.println("-----\nImage iterator after setColor\nExpected is\n" +
                "null 255 null null 0 255 255 255 0 0 255 null 255 255 255 0 255 " +
                "\nActual is");
        for(TreeNode<Short> iter : img1)
            System.out.print(iter+" ");

        Queue<Integer> queue2 = new Queue<>();
        queue2.enqueue(1);
        queue2.enqueue(2);
        queue2.enqueue(3);

        // Dequeue elements from the queue and check if they are dequeued in the correct order
        int dequeue1 = queue2.dequeue();
        int dequeue2 = queue2.dequeue();
        int dequeue3 = queue2.dequeue();

        // Expected output: 1 2 3
        System.out.println("\n-----\nDequeued elements: " + dequeue1 + " " + dequeue2 + " " + dequeue3);

        Queue<Integer> queue3 = new Queue<>();
        queue3.enqueue(1);
        queue3.enqueue(2);
        queue3.enqueue(3);

        // Dequeue all elements from the queue
        while (!queue3.isEmpty()) {
            queue3.dequeue();
        }

        // Expected output: The queue is empty now
        if (queue3.isEmpty()) {
            System.out.println("\n-----\nThe queue is empty now.");
        } else {
            System.out.println("\n-----\nError: The queue should be empty.");
        }



    }

}
