package HEAcalculate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author YEW-Lenovo
 * @Description 存放用于计算的所有数据，包括元素种类、个数，原子半径，价电子数，
 * 原子比，混合焓表等，从excel表中读取
 * @date 2022/2/13
 */
public class ReadData {

    private int eleNum;
    private ArrayList<Float> VECList;
    private ArrayList<Float> rValue;
    private ArrayList<Float> cValue;

    /**
     * @Description 给各参数赋值（除混合焓表）
     */
    public void fieldAssignment() throws HeaException {
        ReadUtils ru = new ReadUtils();

        try {
            //获取文件及工作表
            String filePath = "../parameterConfig.xlsx";
            String sheetName = "元素基本参数";
            //初始化
                ru.initdata(filePath, sheetName);

            //获取元素个数
            eleNum = ru.getColNumByRow(1)-1;
            //获取原子半径
            rValue = ru.getRowByIndex(1);
            //获取价电子数VEC
            VECList = ru.getRowByIndex(2);
            //获取原子个数比
            ArrayList<Float> temp = ru.getRowByIndex(3);
            float tsum = 0;
            for (float tf:temp) {
                tsum += tf;
            }
            for (int i = 0; i < eleNum; i++) {
                temp.set(i,temp.get(i)/tsum);
                cValue = temp;
            }

        } finally {
            ru.CloseResource();
        }
    }

    /**
     * @Description 将excel中的原子对混合焓表读入到数组中
     */
    public List<List<Integer>> getTable() throws HeaException {
        ReadUtils ru = new ReadUtils();
        List<List<Integer>> listdata;
        try {
            //获取文件及工作表
            String filePath = "../parameterConfig.xlsx";
            String sheetName = "原子对混合焓";
                ru.initdata(filePath, sheetName);

            //获取行列
            int tr, tc;
            tr = eleNum+1;
            tc = eleNum+1;//行列相同

            String sdata = null;
            float fdata = 0;//以上string为小数格式，无法直接转为int

            //遍历表格
            listdata = new ArrayList<List<Integer>>();
            for (int i = 1; i < tr; i++) {
                List<Integer> ll = new ArrayList<>();
                for (int j = 1; j < tc; j++) {
                        sdata = ru.getDataByIndex(i, j);

                    try {
                        fdata = Float.parseFloat(sdata);
                    } catch (NumberFormatException e) {
                        throw new HeaException("表中数据存在非数值型错误！");
                    }

                    ll.add(j - 1, (int) fdata);
                }
                listdata.add(i - 1, ll);
            }
            return listdata;
        } finally {
            ru.CloseResource();
        }
    }

    public int getEleNum() {
        return eleNum;
    }

    public ArrayList<Float> getVECList() {
        return VECList;
    }

    public ArrayList<Float> getRvalue() {
        return rValue;
    }

    public ArrayList<Float> getCvalue() {
        return cValue;
    }
}
