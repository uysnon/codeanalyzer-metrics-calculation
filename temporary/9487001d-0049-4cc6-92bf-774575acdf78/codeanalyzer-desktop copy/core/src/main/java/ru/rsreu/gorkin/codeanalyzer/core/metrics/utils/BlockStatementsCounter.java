package ru.rsreu.gorkin.codeanalyzer.core.metrics.utils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.*;

import java.util.ArrayList;
import java.util.List;

public class BlockStatementsCounter {
    public int calculateRecursiveIfStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateIfStatements)
                .sum();
    }

    public int calculateRecursiveSwitchStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateSwitchStatements)
                .sum();
    }

    public int calculateRecursiveForStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateForStatements)
                .sum();
    }

    public int calculateRecursiveForEachStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateForEachStatements)
                .sum();
    }

    public int calculateRecursiveWhileStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateWhileStatements)
                .sum();
    }

    public int calculateRecursiveDoWhileStatements(Node node) {
        List<BlockStmt> blockWithInners = recursiveFindBlockStmts(node);
        return blockWithInners.stream()
                .mapToInt(this::calculateDoWhileStatements)
                .sum();
    }


   private int calculateIfStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof IfStmt)
                .count();
    }

    private int calculateSwitchStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof SwitchStmt)
                .count();
    }

    private int calculateForStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof ForStmt)
                .count();
    }

    private int calculateWhileStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof WhileStmt)
                .count();
    }

    private int calculateDoWhileStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof DoStmt)
                .count();
    }

    private int calculateForEachStatements(BlockStmt blockStmt) {
        return (int) blockStmt.getStatements().stream()
                .filter(statement -> statement instanceof ForEachStmt)
                .count();
    }


    private List<BlockStmt> recursiveFindBlockStmts(Node nodeWithStatements){
        List<BlockStmt> statements = new ArrayList<>();
        if (nodeWithStatements instanceof BlockStmt){
            statements.add((BlockStmt)nodeWithStatements);
        }

        nodeWithStatements.getChildNodes().stream()
                .forEach(currentNode -> statements.addAll(recursiveFindBlockStmts(currentNode)));

        return statements;

    }
}
