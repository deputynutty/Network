package modmuss50.network.util;

public class Math {

    public static int percentage(int MaxValue, int CurrentValue) {
        if (CurrentValue == 0)
            return 0;
        int perint1 = (CurrentValue * 100);
        int perint2 = (perint1 / MaxValue);
        return perint2;
    }

}
