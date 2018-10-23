public class BinarySearch {
    public static void main(String[] args){
        int numArr[] = new int[args.length-1];
        int x = Integer.parseInt(args[0]);
        for (int i = 1; i < args.length; i++) {
            numArr[i-1] = Integer.parseInt(args[i]);
        }
        int ans;
        if(numArr.length==0 || x > numArr[0]) {
            ans = 0;
        } else {
            if (x < numArr[numArr.length - 1]) {
                ans = numArr.length;
            } else {
                //ans = BinarySearch_Rec(numArr,x,0,numArr.length-1);
                ans = BinarySearch_Iter(numArr,x);
            }
        }
        System.out.println(ans);
    }

    public static int BinarySearch_Rec(int[] array, int key, int left, int right)
    {
        int mid = left + (right - left) / 2;
        if (mid == 0 && array[0]==key) {
            return 0;
        }
        if(array[mid]<=key && mid-1 >=0 && array[mid-1]>key ) {
            return mid;
        }
        if (array[mid] <= key) {
            return BinarySearch_Rec(array, key, left, mid);
        } else {
            return BinarySearch_Rec(array, key, mid + 1, right);
        }
    }

    public static int BinarySearch_Iter(int[] array, int key)
    {
        int left = 0;
        int right = array.length;
        int mid = left + (right - left) / 2;

        while(mid-1>=0 && !(array[mid]<=key && array[mid-1]>key ))
        {
            mid = left + (right - left) / 2;
            if (array[mid] <= key)
                right = mid;
            else
                left = mid + 1;
        }

        return mid;
    }

}
