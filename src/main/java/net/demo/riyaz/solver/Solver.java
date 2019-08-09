package net.demo.riyaz.solver;

import net.demo.riyaz.dto.Consignment;

/**
 * The strategy interface used by packer to solve the packing problem.
 */
public interface Solver {
    Consignment solve(Consignment consignment);
}
