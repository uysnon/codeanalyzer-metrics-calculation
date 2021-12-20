package ru.rsreu.gorkin.codeanalyzer.desktop.ui.elements.utils;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class PathFinder {
    private static final double EPS = 0.01;
    private static final double EPS_COORD = 0.5;
    private static final double GAP = 20;

    private class ResultFinding {
        boolean isAvailable;
        Point2D pointIntersection;

        public ResultFinding(boolean isAvailable, Point2D pointIntersection) {
            this.isAvailable = isAvailable;
            this.pointIntersection = pointIntersection;
        }
    }

    private class ResultWithBarrierFinding {
        boolean isAvailable;
        Point2D pointIntersection;
        JComponent barrier;

        public ResultWithBarrierFinding(boolean isAvailable, Point2D pointIntersection, JComponent barrier) {
            this.isAvailable = isAvailable;
            this.pointIntersection = pointIntersection;
            this.barrier = barrier;
        }
    }

    private class TargetPoint {
        boolean isHorizontal;
        Point2D point;

        public TargetPoint(Point2D point, boolean isHorizontal) {
            this.isHorizontal = isHorizontal;
            this.point = point;
        }
    }


    public List<Point2D> findPath(Point2D startDot,
                                  JComponent[] barriers,
                                  JComponent targetComponent) {

        Point2D leftBorderCenterDot = new Point2D.Double(
                targetComponent.getX(),
                targetComponent.getY() + (double) targetComponent.getHeight() / 2);

        Point2D rightBorderCenterDot = new Point2D.Double(
                targetComponent.getX() + targetComponent.getWidth(),
                targetComponent.getY() + (double) targetComponent.getHeight() / 2);

        Point2D topBorderCenterDot = new Point2D.Double(
                targetComponent.getX() + (double) targetComponent.getWidth() / 2,
                targetComponent.getY());

        Point2D bottomBorderCenterDot = new Point2D.Double(
                targetComponent.getX() + (double) targetComponent.getWidth() / 2,
                targetComponent.getY() + targetComponent.getHeight());

        List<TargetPoint> endPoints = new ArrayList<>();
        endPoints.add(new TargetPoint(leftBorderCenterDot, false));
//        endPoints.add(new TargetPoint(rightBorderCenterDot, false));
//        endPoints.add(new TargetPoint(topBorderCenterDot, true));
//        endPoints.add(new TargetPoint(bottomBorderCenterDot, true));

        List<Point2D> minPath = findPath(startDot, barriers, endPoints.get(0));
        for (int i = 1; i < endPoints.size(); i++) {
            List<Point2D> currentPath = findPath(startDot, barriers, endPoints.get((i)));
            if (currentPath.size() < minPath.size()) {
                minPath = currentPath;
            }
        }

        return minPath;
    }

    private List<Point2D> findPath(Point2D startDot,
                                   JComponent[] barriers,
                                   TargetPoint targetPoint) {
        double GAP_X = targetPoint.isHorizontal ? 0 : GAP;
        double GAP_Y = !targetPoint.isHorizontal ? 0 : GAP;

        Point2D targetDot = targetPoint.point;

        List<Point2D> pathPoints = new ArrayList<>();
        pathPoints.add(startDot);
        Point2D previousDot = startDot;
        Point2D currentDot = null;
        boolean alternativeMode = false;
        JComponent barrier = null;
        while (true) {

            if (targetDot.getX() - previousDot.getX() >= 0) {
                currentDot = new Point2D.Double(targetDot.getX() - GAP_X, previousDot.getY());
                if (alternativeMode) {
                    currentDot = new Point2D.Double(barrier.getX() + barrier.getWidth() + GAP, previousDot.getY());
                    alternativeMode = false;
                }
            } else {
                currentDot = new Point2D.Double(targetDot.getX() + GAP_X, previousDot.getY());
                if (alternativeMode) {
                    currentDot = new Point2D.Double(barrier.getX() - GAP, previousDot.getY());
                    alternativeMode = false;
                }
            }
            ResultWithBarrierFinding localResultFinding = (isPathAvailable(previousDot, barriers, currentDot));
            if (!localResultFinding.isAvailable) {
                alternativeMode = true;
                barrier = localResultFinding.barrier;
                if (targetDot.getX() - previousDot.getX() > 0) {
                    currentDot = new Point2D.Double(localResultFinding.pointIntersection.getX() - GAP, localResultFinding.pointIntersection.getY());
                } else {
                    currentDot = new Point2D.Double(localResultFinding.pointIntersection.getX() + GAP, localResultFinding.pointIntersection.getY());
                }
            }
            pathPoints.add(new Point2D.Double(currentDot.getX(), currentDot.getY()));
            previousDot = new Point2D.Double(currentDot.getX(), currentDot.getY());
            if ((Math.abs(currentDot.getX() - targetDot.getX()) <= GAP) && (Math.abs(currentDot.getY() - targetDot.getY()) <= GAP)) {
                break;
            }

            if (targetDot.getY() - previousDot.getY() < 0) {
                currentDot = new Point2D.Double(previousDot.getX(), targetDot.getY() + GAP_Y);
                if (alternativeMode) {
                    currentDot = new Point2D.Double(previousDot.getX(), barrier.getY() - GAP);
                    alternativeMode = false;
                }
            } else {
                currentDot = new Point2D.Double(previousDot.getX(), targetDot.getY() - GAP_Y);
                if (alternativeMode) {
                    currentDot = new Point2D.Double(previousDot.getX(), barrier.getY() + barrier.getHeight() + GAP);
                    alternativeMode = false;
                }
            }
            ResultWithBarrierFinding local1ResultFinding = (isPathAvailable(previousDot, barriers, currentDot));
            if (!local1ResultFinding.isAvailable) {
                alternativeMode = true;
                barrier = local1ResultFinding.barrier;
                if (targetDot.getY() - previousDot.getY() < 0) {
                    currentDot = new Point2D.Double(local1ResultFinding.pointIntersection.getX(), local1ResultFinding.pointIntersection.getY() + GAP);
                } else {
                    currentDot = new Point2D.Double(local1ResultFinding.pointIntersection.getX(), local1ResultFinding.pointIntersection.getY() - GAP);
                }
            }
            pathPoints.add(new Point2D.Double(currentDot.getX(), currentDot.getY()));
            previousDot = new Point2D.Double(currentDot.getX(), currentDot.getY());
            if ((Math.abs(currentDot.getX() - targetDot.getX()) <= GAP) && (Math.abs(currentDot.getY() - targetDot.getY()) <= GAP)) {
                break;
            }
        }

        pathPoints.add(targetDot);
        return pathPoints;
    }

    private ResultWithBarrierFinding isPathAvailable(Point2D startDot,
                                                     JComponent[] barriers,
                                                     Point2D endDot) {
        if (endDot.getX() - startDot.getX() > EPS_COORD) {
            Arrays.sort(barriers, (component1, component2) -> component1.getX() - component2.getX());
        }
        if (endDot.getX() - startDot.getX() < -EPS_COORD) {
            Arrays.sort(barriers, (component1, component2) -> component2.getX() - component1.getX());
        }
        if (endDot.getY() - startDot.getY() > EPS_COORD) {
            Arrays.sort(barriers, (component1, component2) -> component2.getY() - component1.getY());
        }
        if (endDot.getY() - startDot.getY() < -EPS_COORD) {
            Arrays.sort(barriers, (component1, component2) -> component1.getY() - component2.getY());
        }

        for (JComponent barrier : barriers) {
            ResultFinding localResultFinding = findInterceptPieceAndRectangle(
                    startDot,
                    endDot,
                    barrier
            );
            if (!localResultFinding.isAvailable) {
                return new ResultWithBarrierFinding(localResultFinding.isAvailable, localResultFinding.pointIntersection, barrier);
            }
        }
        return new ResultWithBarrierFinding(true, null, null);
    }

    private ResultFinding findInterceptPieceAndRectangle(Point2D pieceStart, Point2D pieceEnd,
                                                         JComponent rectangle) {

        if (pieceEnd.getX() - pieceStart.getX() > EPS_COORD) {
            return intersect(
                    pieceStart,
                    pieceEnd,
                    new Point2D.Double(rectangle.getX(), rectangle.getY()),
                    new Point2D.Double(rectangle.getX(), rectangle.getY() + rectangle.getHeight()));
        }
        if (pieceEnd.getX() - pieceStart.getX() < -EPS_COORD) {
            return intersect(
                    pieceStart,
                    pieceEnd,
                    new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY()),
                    new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight()));
        }
        if (pieceEnd.getY() - pieceStart.getY() > EPS_COORD) {
            return intersect(
                    pieceStart,
                    pieceEnd,
                    new Point2D.Double(rectangle.getX(), rectangle.getY()),
                    new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY()));
        }
        if (pieceEnd.getY() - pieceStart.getY() < -EPS_COORD) {
            return intersect(
                    pieceStart,
                    pieceEnd,
                    new Point2D.Double(rectangle.getX(), rectangle.getY() + rectangle.getHeight()),
                    new Point2D.Double(rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight()));
        }

        throw new IllegalArgumentException();
    }


    private double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    private boolean between(double a, double b, double c) {
        return (min(a, b) <= c + EPS) && (c <= max(a, b) + EPS);
    }


    private boolean intersect_1(double a, double b, double c, double d) {
        if (a > b) {
            double p = a;
            a = b;
            b = p;
        }
        if (c > d) {
            double p = c;
            c = d;
            d = p;
        }
        return max(a, c) <= min(b, d);
    }

    private ResultFinding intersect(Point2D a, Point2D b, Point2D c, Point2D d) {
        double A1 = a.getY() - b.getY(), B1 = b.getX() - a.getX(), C1 = -A1 * a.getX() - B1 * a.getY();
        double A2 = c.getY() - d.getY(), B2 = d.getX() - c.getX(), C2 = -A2 * c.getX() - B2 * c.getY();
        double zn = det(A1, B1, A2, B2);
        if (zn != 0) {
            double x = -det(C1, B1, C2, B2) * 1. / zn;
            double y = -det(A1, C1, A2, C2) * 1. / zn;
            return new ResultFinding(
                    !(between(a.getX(), b.getX(), x) && between(a.getY(), b.getY(), y)
                            && between(c.getX(), d.getX(), x) && between(c.getY(), d.getY(), y)),
                    new Point2D.Double(x, y));
        } else
            return new ResultFinding((det(A1, C1, A2, C2) == 0 && det(B1, C1, B2, C2) == 0
                    && intersect_1(a.getX(), b.getX(), c.getX(), d.getX())
                    && intersect_1(a.getY(), b.getY(), c.getY(), d.getY())),
                    new Point2D.Double(20, 20));
    }

}
