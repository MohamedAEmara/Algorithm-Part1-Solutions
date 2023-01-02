import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private int openSites = 0;
    private int n_uf;
    private boolean grid[][];
    private WeightedQuickUnionUF uf;
    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("Index out of bounds");
        N = n;
        grid = new boolean[N][N];
        for (int i=0;i<n;i++)
            for (int j=0;j<n;j++)
                grid[i][j] = false;

        n_uf = N*N+2;
        uf = new WeightedQuickUnionUF(n_uf);
        for (int i=0;i<N;i++)
        {
            int encoded_val = encode(0, i);
            uf.union(encoded_val, n_uf-2);
        }
        for (int i=0;i<N;i++)
        {
            int encoded_val = encode(N-1, i);
            uf.union(encoded_val, n_uf-1);
        }
    }

    private boolean valid(int i, int j)
    {
        if (i <= 0 || i > N || j <= 0 || j > N)
            return false;
        return true;
    }

    public void open(int i, int j)
    {
        if  (!valid(i, j))
            throw new IllegalArgumentException("Index out of bounds");
        int ii = i-1;
        int jj = j-1;
        if (!grid[ii][jj])
        {
            grid[ii][jj] = true;
            openSites ++;
            if (valid(i+1, j) && isOpen(i+1, j))
                uf.union(encode(ii+1, jj), encode(ii, jj));
            if (valid(i-1, j) && isOpen(i-1, j))
                uf.union(encode(ii-1, jj), encode(ii, jj));
            if (valid(i, j+1) && isOpen(i, j+1))
                uf.union(encode(ii, jj), encode(ii, jj+1));
            if (valid(i, j-1) && isOpen(i, j-1))
                uf.union(encode(ii, jj), encode(ii, jj-1));
        }

    }

    public boolean isOpen(int i, int j)
    {
        if (!valid(i, j))
            throw new IllegalArgumentException("Index out of bounds");
        return grid[i-1][j-1];
    }

    public boolean isFull(int i, int j)
    {
        if (!valid(i, j))
            throw new IllegalArgumentException("Index out of bounds");

        int encoded_val = encode(i-1, j-1);
        if  (uf.find(encoded_val) == uf.find(n_uf-2) && isOpen(i, j))
            return true;
        else
            return false;
     //   return (uf.connected(encoded_val, n_uf - 2)  && isOpen(i, j));
    }

    public int numberOfOpenSites()
    {
        return openSites;
    }

    public boolean percolates()
    {
        return uf.find(n_uf - 1) == uf.find(n_uf - 2);
//        return uf.connected(n_uf - 1 , n_uf - 2);
    }


    private int encode(int i, int j)
    {
        return (i * N + j);
    }
}
