package HEAcalculate;

/**
 * @author YEW-Lenovo
 * @Description
 * @date 2022/2/13
 */
public class view {
    public static void main(String[] args) {
        Calculation cu = new Calculation();

        System.out.println("********高熵合金参数计算工具********");
        System.out.println();

        try {
            cu.Assignment();//参数赋值

        //********************混合熵计算*********************
            float SResult = (float) cu.CalculSMix();
            String TRResult = String.format("%.2f", SResult / 8.314);//转换为xxR，并保留两位小数
            System.out.println(
                    "混合熵Smix的计算结果为：" + SResult + " J/molK (即" + TRResult + "R)");

        //******************混合焓计算************************
            float HResult = (float) cu.CalculHMix();
            System.out.println("混合焓Hmix的计算结果为：" + HResult + " KJ/mol");

        //******************原子半径差计算************************
            float RResult = (float) cu.CalculDeltaR();
            System.out.println("原子半径差为：" + String.format("%.2f", RResult * 100) + "%");

        //******************平均价电子浓度计算************************
            float VResult = (float) cu.CalculVEC();
            System.out.println("平均价电子浓度VEC为：" + String.format("%.2f", VResult));
        } catch (HeaException e) {
            System.out.println(e.getMessage());
        }

        System.out.println();
        System.out.println("********计算完成********");
    }
}
