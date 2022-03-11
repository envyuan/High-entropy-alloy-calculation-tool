package HEAcalculate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YEW-Lenovo
 * @Description
 * @date 2022/2/15
 */
public class Calculation {
    ReadData rd = new ReadData();

    int elenum;
    ArrayList<Float> rvalue;
    ArrayList<Float> vecList;
    ArrayList<Float> cvalue;
    List<List<Integer>> htable;

    public void Assignment() throws HeaException {
        rd.fieldAssignment();

        elenum = rd.getEleNum();
        rvalue = rd.getRvalue();
        vecList = rd.getVECList();
        cvalue = rd.getCvalue();
        htable = rd.getTable();
    }

    /**
     * @author YEW-Lenovo
     * @Description 计算混合熵
     * @date 2021/12/2
     */
    public double CalculSMix() throws HeaException {
        if (cvalue == null) {
            throw new HeaException("获取原子个数比时出错！");
        } else {
            double tempsum = 0, sum = 0;
            for (float ci : cvalue) {
                if (ci != 0) {
                    tempsum = -(8.314 * ci * Math.log(ci));
                    sum += tempsum;
                }
            }
            return sum;
        }

    }

    /**
     * @author YEW-Lenovo
     * @Description 计算混合焓
     * @date 2021/12/2
     */
    public float CalculHMix() throws HeaException {
        if (elenum == 0) {
            throw new HeaException("获取元素个数时出错！");
        } else {
            if (htable == null){
                throw new HeaException("获取原子对混合焓时出错！");
            } else {
                float tempsum = 0, sum = 0;
                for (int i = 0; i < elenum - 1; i++) {
                    for (int j = i + 1; j < elenum; j++) {
                        tempsum = 4 * cvalue.get(i) * cvalue.get(j) * htable.get(i).get(j);
                        sum += tempsum;
                    }
                }
                return sum;
            }
        }
    }

    /**
     * @author YEW-Lenovo
     * @Description
     * @date 2021/12/2
     */
    public double CalculDeltaR() throws HeaException {
        if (rvalue == null) {
            throw new HeaException("获取原子半径时出错！");
        } else {
            double rav = 0, tempsum = 0, sum = 0;
            for (int j = 0; j < elenum; j++) {
                tempsum = cvalue.get(j) * rvalue.get(j);
                rav += tempsum;
            }
            for (int i = 0; i < elenum; i++) {
                tempsum = cvalue.get(i) * Math.pow((1 - rvalue.get(i) / rav), 2);
                sum += tempsum;
            }
            return Math.sqrt(sum);
        }

    }

    /**
     * @author YEW-Lenovo
     * @Description
     * @date 2021/12/21
     */
    public float CalculVEC() throws HeaException {
        if (vecList == null) {
            throw new HeaException("获取价电子数据时出错！");
        } else {
            float vecvalue = 0, tempsum = 0;
            for (int i = 0; i < elenum; i++) {
                vecvalue = cvalue.get(i) * vecList.get(i);
                tempsum += vecvalue;
            }
            return tempsum;
        }
    }

//    public void show(){
//        System.out.println(elenum);
//        System.out.println(rvalue);
//        System.out.println(cvalue);
//        System.out.println(vecList);
//        System.out.println(htable);
//    }
}
