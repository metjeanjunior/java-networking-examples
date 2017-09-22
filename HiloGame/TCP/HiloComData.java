import java.io.Serializable;

public class HiloComData implements Serializable {

	private String name;
	private int guess, hi, lo;
	private boolean isCorrect, isLo;

	public HiloComData(String name)	{	this.name = name;	}

	public String getName()	{	return name;	}

	public void setLoHi(int lo, int hi)
	{
		this.lo = lo;
		this.hi = hi;
	}
	
	public void setCorrect()	{	isCorrect = true;	}

	public void setGuess(int guess)	{	this.guess = guess;	}
	public int getGuess()	{	return guess;	}

	public void setLoHiBool(boolean isLo)	{	this.isLo = isLo;	}

	public boolean isCorrect()	{	return isCorrect;	}
	// Unused Hi since if !correct || low -> high
	public boolean isLo()	{	return isLo;	}

	public int getLo()	{	return lo;	}
	public int getHi()	{	return hi;	}
}