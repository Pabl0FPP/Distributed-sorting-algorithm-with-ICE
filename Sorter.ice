module Demo
{

    interface MergeSort{
        int[] mergeSort(int[] items);
    }

    interface QuickSort{
        int[] quickSort(int[] items);
    }

    interface BucketSort{
        int[] sort(int[] items);
    }

    interface ClientHandler{
        string response(string s);
    }


}