package main;
import libcint.LibcintLibrary;
import org.junit.Test;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        int i = LibcintLibrary.INSTANCE.CINTlen_cart(1);
        System.out.println(i);
    }
    @Test
    public void test1(){
        LibcintLibrary lib = LibcintLibrary.INSTANCE;
        int natm = 2;
        int nbas = 4;
        int ATM_SLOTS = LibcintLibrary.ATM_SLOTS; // Define ATM_SLOTS
        int BAS_SLOTS = LibcintLibrary.BAS_SLOTS; // Define BAS_SLOTS
        int CHARGE_OF = LibcintLibrary.CHARGE_OF;
        int PTR_COORD = LibcintLibrary.PTR_COORD;
        int ATOM_OF = LibcintLibrary.ATOM_OF;
        int ANG_OF = LibcintLibrary.ANG_OF;
        int NPRIM_OF = LibcintLibrary.NPRIM_OF;
        int NCTR_OF = LibcintLibrary.NCTR_OF;
        int PTR_EXP = LibcintLibrary.PTR_EXP;
        int PTR_COEFF = LibcintLibrary.PTR_COEFF;
        int[] atm = new int[natm * ATM_SLOTS];
        int[] bas = new int[nbas * BAS_SLOTS];
        double[] env = new double[10000];

        int i, n, off;
        off = 20; // Set off to PTR_ENV_START's value (20 in this case)
        i = 0;
        atm[CHARGE_OF + ATM_SLOTS * i] = 1;// 存储第一个原子的电荷
        atm[PTR_COORD + ATM_SLOTS * i] = off;//在给定的代码中，PTR_COORD 是一个偏移量（offset），用于确定环境变量（env数组）中存储原子坐标的位置
        env[off + 0] = 0.0; // x (Bohr)  //第一个原子的x坐标
        env[off + 1] = 0.0; // y (Bohr)  // 第一个原子的 y坐标
        env[off + 2] = -0.8; // z (Bohr)  // 第一个原子的z坐标
        i++;
        off += 3;
        atm[CHARGE_OF + ATM_SLOTS * i] = 1; //存储第二个原子的电荷
        atm[PTR_COORD + ATM_SLOTS * i] = off;//在给定的代码中，PTR_COORD 是一个偏移量（offset），用于确定环境变量（env数组）中存储原子坐标的位置
        env[off + 0] = 0.0;         //第二个原子的x坐标
        env[off + 1] = 0.0;         // 第二个原子的 y坐标
        env[off + 2] = 0.8; // (Bohr)       // 第二个原子的z坐标
        i++;
        off += 3;

        n = 0;
        // basis #0, 3s -> 2s
        /**
         * bas(1,i)：第i个shell所属的原子编号；
         * bas(2,i)：角动量量子数，即S为0，P为1，D为2等等；  下面为0
         * bas(3,i)：该shell中所含的原始高斯基函数的数目；   下面为3
         * bas(4,i)：该shell中所含的收缩高斯基函数的数目；   下面为2
         * bas(5,i)：spinor GTO的kappa值；  下面为0，没用到
         * bas(6,i)：env数组中记录原始高斯基函数指数的位置；   第一个是26(20-25存储了两个H原子的坐标）
         * bas(7,i)：env数组中记录原始高斯基函数系数的位置，注意这个数值并不是从基组文件中直接读过来的数值，需要进行一定的转换，与基函数的归一化有关，具体可参考PySCF中的处理方法。
         * bas(8,i)：未使用。
         */
        bas[ATOM_OF + BAS_SLOTS * n] = 0;   // ATOM_OF=0
        bas[ANG_OF + BAS_SLOTS * n] = 0;        // ANG_OF=1    角动量量子数
        bas[NPRIM_OF + BAS_SLOTS * n] = 3;      //NPRIM_OF=2        该shell中所含的原始高斯基函数的数目
        bas[NCTR_OF + BAS_SLOTS * n] = 2;       //NCTR_OF=3         该shell中所含的收缩高斯基函数的数目
        bas[PTR_EXP + BAS_SLOTS * n] = off;     // PTR_EXP=5        env数组中记录原始高斯基函数指数的位置
        env[off + 0] = 6.0;                 //设置原始高斯基函数指数
        env[off + 1] = 2.0;                 //设置原始高斯基函数指数
        env[off + 2] = 0.8;                 //设置原始高斯基函数指数
        off += 3;
        bas[PTR_COEFF + BAS_SLOTS * n] = off;       //PTR_COEFF = 6     env数组中记录原始高斯基函数系数的位置
        // 下面：求出gto的归一化常数，然后乘以一个小于1的数，形成原始高斯基函数的系数
        env[off + 0] = 0.7 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 0]); //存储原始高斯基函数系数
        env[off + 1] = 0.6 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 1]);  //存储原始高斯基函数系数
        env[off + 2] = 0.5 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 2]);  //存储原始高斯基函数系数
        env[off + 3] = 0.4 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 0]);  //存储原始高斯基函数系数
        env[off + 4] = 0.3 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 1]);  //存储原始高斯基函数系数
        env[off + 5] = 0.2 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n] + 2]);  //存储原始高斯基函数系数




        off += 6;
        n++;

        // basis #1
        bas[ATOM_OF + BAS_SLOTS * n] = 0;
        bas[ANG_OF + BAS_SLOTS * n] = 1;
        bas[NPRIM_OF + BAS_SLOTS * n] = 1;
        bas[NCTR_OF + BAS_SLOTS * n] = 1;
        bas[PTR_EXP + BAS_SLOTS * n] = off;
        env[off + 0] = 0.9;
        off += 1;
        bas[PTR_COEFF + BAS_SLOTS * n] = off;
        env[off + 0] = 1.0 * lib.CINTgto_norm(bas[ANG_OF + BAS_SLOTS * n], env[bas[PTR_EXP + BAS_SLOTS * n]]);
        off += 1;
        n++;

        // basis #2 == basis #0
        bas[ATOM_OF + BAS_SLOTS * n] = 1;
        bas[ANG_OF + BAS_SLOTS * n] = bas[ANG_OF + BAS_SLOTS * 0];
        bas[NPRIM_OF + BAS_SLOTS * n] = bas[NPRIM_OF + BAS_SLOTS * 0];
        bas[NCTR_OF + BAS_SLOTS * n] = bas[NCTR_OF + BAS_SLOTS * 0];
        bas[PTR_EXP + BAS_SLOTS * n] = bas[PTR_EXP + BAS_SLOTS * 0];
        bas[PTR_COEFF + BAS_SLOTS * n] = bas[PTR_COEFF + BAS_SLOTS * 0];
        n++;

        // basis #3 == basis #1
        bas[ATOM_OF + BAS_SLOTS * n] = 1;
        bas[ANG_OF + BAS_SLOTS * n] = bas[ANG_OF + BAS_SLOTS * 1];
        bas[NPRIM_OF + BAS_SLOTS * n] = bas[NPRIM_OF + BAS_SLOTS * 1];
        bas[NCTR_OF + BAS_SLOTS * n] = bas[NCTR_OF + BAS_SLOTS * 1];
        bas[PTR_EXP + BAS_SLOTS * n] = bas[PTR_EXP + BAS_SLOTS * 1];
        bas[PTR_COEFF + BAS_SLOTS * n] = bas[PTR_COEFF + BAS_SLOTS * 1];
        n++;

        int  j, di, dj;
        int[] shls = new int[4];
        // 积分结果存在这里
        double[] buf;

        i = 0;
        shls[0] = i;
        di = lib.CINTcgto_cart(i, bas);
        j = 1;
        shls[1] = j;
        dj = lib.CINTcgto_cart(j, bas);

        buf = new double[di * dj * 3];
        System.out.println("buf ==============");
        System.out.println(Arrays.toString(buf)+buf.length);
        System.out.println("shls ==============");
        System.out.println(Arrays.toString(shls)+shls.length);
        System.out.println("atm ==============");
        System.out.println(Arrays.toString(atm)+atm.length);
        System.out.println("natm ==============");
        System.out.println(natm);
        System.out.println("bas ==============");
        System.out.println(Arrays.toString(bas)+bas.length);
        System.out.println("nbas ==============");
        System.out.println(nbas);
        System.out.println("env ==============");
        System.out.println(Arrays.toString(env)+env.length);
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
        int result = lib.cint1e_ipnuc_cart(DoubleBuffer.wrap(buf), IntBuffer.wrap(shls), IntBuffer.wrap(atm), natm, IntBuffer.wrap(bas), nbas, DoubleBuffer.wrap(env));
        System.out.println("buf ==============");
        System.out.println(Arrays.toString(buf)+buf.length);
        System.out.println("shls ==============");
        System.out.println(Arrays.toString(shls)+shls.length);
        System.out.println("atm ==============");
        System.out.println(Arrays.toString(atm)+atm.length);
        System.out.println("natm ==============");
        System.out.println(natm);
        System.out.println("bas ==============");
        System.out.println(Arrays.toString(bas)+bas.length);
        System.out.println("nbas ==============");
        System.out.println(nbas);
        System.out.println("env ==============");
        System.out.println(Arrays.toString(env)+env.length);


        if (result != 0) {
            System.out.println("This gradient integral is not 0.================"+result);
        } else {
            System.out.println("This integral is 0.");
        }
    }



}