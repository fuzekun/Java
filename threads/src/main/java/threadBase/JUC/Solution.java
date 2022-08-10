package threadBase.JUC;

import threadBase.model.Philosopher;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2022/6/19 22:10
 * @Description:
 */
public class Solution {
    public boolean isPointInPolygon(double x, double y, double[] coords) {
        List<SpatialRelationUtil.Point>points = new ArrayList<>();
        int n = coords.length;
        for (int i = 0; i < n; i += 2) {
            SpatialRelationUtil.Point p = new SpatialRelationUtil.Point(coords[i], coords[i + 1]);
            points.add(p);
        }
        SpatialRelationUtil.Point p = new SpatialRelationUtil.Point(x, y);
        boolean flag = SpatialRelationUtil.isPointInPolygonBoundary(points, p) || SpatialRelationUtil.isPolygonContainsPoint(points, p);
        return flag;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        double[] coords = {0,0,0,4,4,4,2,2,4,0,0,0};
        boolean flag = s.isPointInPolygon(1, 3, coords);
        System.out.println(flag);
    }
}



class SpatialRelationUtil {
    private SpatialRelationUtil() {}


    public static class Point {

        public Point(double a, double b) {
            x = a;
            y = b;
        }

        public double x;
        public double y;
    }

    /**
     * 返回一个点是否在一个多边形区域内
     *
     * @param mPoints 多边形坐标点列表
     * @param point 待判断点
     * @return true 多边形包含这个点,false 多边形未包含这个点。
     */
    public static boolean isPolygonContainsPoint(List<Point> mPoints, Point point) {
        int nCross = 0;
        for (int i = 0; i < mPoints.size(); i++) {
            Point p1 = mPoints.get(i);
            Point p2 = mPoints.get((i + 1) % mPoints.size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.y == p2.y) continue;
            // point 在p1p2 底部 --> 无交点
            if (point.y < Math.min(p1.y, p2.y)) continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.y >= Math.max(p1.y, p2.y)) continue;
            // 求解 point点水平线与当前p1p2边的交点的 X 坐标 通过前面几个if条件筛选,这里的如果求出来有交点一定在p1p2连接线上,而不是延长线上.
            double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
            if (x > point.x) // 当x=point.x时,说明point在p1p2线段上
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }

    /**
     * 返回一个点是否在一个多边形边界上
     *
     * @param mPoints 多边形坐标点列表
     * @param point 待判断点
     * @return true 点在多边形边上,false 点不在多边形边上。
     */
    public static boolean isPointInPolygonBoundary(List<Point> mPoints, Point point) {
        for (int i = 0; i < mPoints.size(); i++) {
            Point p1 = mPoints.get(i);
            Point p2 = mPoints.get((i + 1) % mPoints.size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数

            // point 在p1p2 底部 --> 无交点
            if (point.y < Math.min(p1.y, p2.y)) continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.y > Math.max(p1.y, p2.y)) continue;

            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.y == p2.y) {
                double minX = Math.min(p1.x, p2.x);
                double maxX = Math.max(p1.x, p2.x);
                // point在水平线段p1p2上,直接return true
                if ((point.y == p1.y) && (point.x >= minX && point.x <= maxX)) {
                    return true;
                }
            } else { // 求解交点
                double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
                if (x == point.x) // 当x=point.x时,说明point在p1p2线段上
                    return true;
            }
        }
        return false;
    }
}