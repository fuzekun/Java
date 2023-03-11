package simpledb.optimizer;
import simpledb.optimizer.JoinOptimizer;
import simpledb.optimizer.LogicalJoinNode;

import java.util.List;

/** Class returned by {@link JoinOptimizer# computeCostAndCardOfSubplan} specifying the
    cost and cardinality of the optimal plan represented by plan.
*/
public class CostCard {
    /** The cost of the optimal subplan */
    public double cost;                                         // 连接顺序下的代价
    /** The cardinality of the optimal subplan */
    public int card;                                            // 连接顺序下的基数
    /** The optimal subplan */
    public List<LogicalJoinNode> plan;                              // 查询计划
}
