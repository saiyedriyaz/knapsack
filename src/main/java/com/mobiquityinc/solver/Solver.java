package com.mobiquityinc.solver;

import com.mobiquityinc.dto.Consignment;

/**
 * The strategy interface used by packer to solve the packing problem.
 */
public interface Solver {
    Consignment solve(Consignment consignment);
}
