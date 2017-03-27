/*
 * CSCI320 - Networking and Distributed Computing - Spring 2017
 * Instructor: Thyago Mota
 * Description: Prg02 - Performance Monitor Server
 * Your name:
 */

import mpi.*;



class PerformanceMonitorServer {
    private Comm comm;
    static String hostname;

    public PerformanceMonitorServer(Comm comm) {
        this.comm = comm;
    }

    void run() throws MPIException {
        int np = this.comm.getSize();
        int rank = this.comm.getRank();
        int data[] = new int[np];

        data[0] = rank;
        this.comm.gather(data, 1, MPI.LONG_INT, 0);

        if (rank == 0) {
            for (int i = 0; i < np; i++)
                System.out.println(data[i]);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            hostname = args[i];
        }
        try {
            MPI.Init(args);
            Comm comm = MPI.COMM_WORLD;
            new PerformanceMonitorServer(comm).run();
            MPI.Finalize();
        } catch (MPIException ex) {
            System.out.println(ex);
        }

    }
}
