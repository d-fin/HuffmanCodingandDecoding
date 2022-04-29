package Classes;

public class MergeSort {
    public void sort(Node arr[], int l, int r)
    {
        if (l < r) {
            int m = l + (r - l) / 2;
            sort(arr, l, m);
            sort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }
   
    void merge(Node arr[], int l, int m, int r)
    {
        int n1 = m - l + 1;
        int n2 = r - m;
  
        Node L[] = new Node[n1];
        Node R[] = new Node[n2];
  
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
  
        int i = 0, j = 0;
  
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i].frequency <= R[j].frequency) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
        
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
  
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
}
