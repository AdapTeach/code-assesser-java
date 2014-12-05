package com.adapteach.codeassesser.compile;

import lombok.Data;

@Data
public class CompilationUnit {

    public enum Kind {
        CLASS, INTERFACE, ENUM;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }
    }

    private Kind kind = Kind.CLASS;

    private String name;

    private String code;

    public String getMissingMessage() {
        return "Missing " + getName() + " " + getKind();
    }

    public CompilationUnit safeCopy() {
        CompilationUnit safeCopy = new CompilationUnit();
        safeCopy.setKind(kind);
        safeCopy.setName(name);
        safeCopy.setCode(safeCode());
        return safeCopy;
    }

    private String safeCode() {
        String safeCode = code;
        safeCode = removeAllComments(safeCode);
        safeCode = protectAgainstInfiniteWhileLoops(safeCode);
        safeCode = protectAgainstInfiniteDoLoops(safeCode);
        safeCode = protectAgainstInfiniteForLoops(safeCode);
        return safeCode;
    }

    private String removeAllComments(String code) {
        String safeCode = code;
        safeCode = removeLineComments(safeCode);
        safeCode = removeBlockComments(safeCode);
        safeCode = removeDocComments(safeCode);
        return safeCode;
    }

    private String removeLineComments(String code) {
        return code; // TODO Implement
    }

    private String removeBlockComments(String code) {
        return code; // TODO Implement
    }

    private String removeDocComments(String code) {
        return code; // TODO Implement
    }

    private String protectAgainstInfiniteWhileLoops(String code) {
//        StringBuilder source = new StringBuilder(code);
//        StringBuilder destination = new StringBuilder();
//        while (source.indexOf("while") >= 0) {
//            int whilePos = source.indexOf("while");
//            int semicolonPos = source.indexOf(";", whilePos);
//            int openingBracePos = source.indexOf("{", whilePos);
//            if (openingBracePos > 0 && openingBracePos < semicolonPos) { // construct has opening braces
//                destination.append(source.subSequence(0, openingBracePos + 1));
//                source.delete(0, openingBracePos + 1);
//                destination.append("if (Thread.currentThread().isInterrupted()) break;");
//            } else { // construct has no opening braces
//                throw new CodeStyleException("while statements should always be used with braces");
//            }
//        }
//        destination.append(source);
//        return destination.toString();
        return code;
    }

    private String protectAgainstInfiniteDoLoops(String code) {
        return code;
    }

    private String protectAgainstInfiniteForLoops(String code) {
//        StringBuilder source = new StringBuilder(code);
//        StringBuilder destination = new StringBuilder();
//        while (source.indexOf("for") >= 0) {
//            int forPos = source.indexOf("for");
//            int openingBrace = source.indexOf("{", forPos);
//            destination.append(source.subSequence(0, openingBrace + 1));
//            source.delete(0, openingBrace + 1);
//            destination.append("if (Thread.currentThread().isInterrupted()) break;");
//        }
//        destination.append(source);
//        return destination.toString();
        return code;
    }

}
