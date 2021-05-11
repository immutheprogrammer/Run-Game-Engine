package run;

public class SimpleMath {

    // Why does java not have a clamp function??????
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }
}
