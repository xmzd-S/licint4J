package libcint;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;
/**
 * <i>native declaration : C:\Users\10453\Desktop\notebook\libcint\cmake-build-debug\include\cint.h:5</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.com/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> , <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class PairData extends Structure {
	/** C type : double[3] */
	public double[] rij = new double[3];
	public double eij;
	public double cceij;
	public PairData() {
		super();
	}
	protected List<? > getFieldOrder() {
		return Arrays.asList("rij", "eij", "cceij");
	}
	/** @param rij C type : double[3] */
	public PairData(double rij[], double eij, double cceij) {
		super();
		if ((rij.length != this.rij.length)) 
			throw new IllegalArgumentException("Wrong array size !");
		this.rij = rij;
		this.eij = eij;
		this.cceij = cceij;
	}
	public PairData(Pointer peer) {
		super(peer);
	}
	public static class ByReference extends PairData implements Structure.ByReference {
		
	};
	public static class ByValue extends PairData implements Structure.ByValue {
		
	};
}
