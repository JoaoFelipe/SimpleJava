/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplejava;

import history.History;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Joao
 */
public class Code {

    public static Pattern commentPattern = Pattern.compile(RegexUtil._comments(), Pattern.CASE_INSENSITIVE | Pattern.DOTALL | Pattern.MULTILINE | Pattern.UNICODE_CASE | Pattern.CANON_EQ);
    String text;
    String textWithComment;
    List<Block> blocks;
    
    List<CommentBlock> comments;
    List<Classe> classes;
    List<Import> imports;
    List<Package> packages;
    List<Method> methods;
    
    History history;
    
    Classe mainClass = null;

    public static Code readFile(Scanner scanner) throws SyntaxException {
        String temp = "";
        while (scanner.hasNextLine()) {
            temp += scanner.nextLine() + '\n';
        }
        temp = temp.substring(0, temp.length() - 1);
        return new Code(temp);
    }

    public Code(String textWithComment) throws SyntaxException {
        this.textWithComment = textWithComment;
        this.text = "";
        history = new History();
        
        comments = new ArrayList<CommentBlock>();
        Matcher matcher = commentPattern.matcher(textWithComment);
        while (matcher.find()) {
            comments.add(new CommentBlock(matcher.start(), matcher.group()));
            history.remove(matcher.start(), matcher.start()-matcher.end());
        }
        for (int i = 0; i < textWithComment.length(); i++) {
//            comments.get(0).containPosition(i);
            if (!new CharacterBlock(i).isInnerInList(comments)) {
                this.text += textWithComment.charAt(i);
            }
        }

    }

    private static List<Block> merge(List<? extends Block>... blocks) {
        List<Block> result = new ArrayList<Block>();
        for (List<? extends Block> abstractBlock : blocks) {
            result.addAll(abstractBlock);
        }
        return result;
    }

    public void extractBlocks() throws SyntaxException {
        classes = Classe.extractClasses(this);
        imports = Import.extractImports(text);
        packages = Package.extractPackages(this);
        methods = Method.extractMethods(text);
        blocks = merge(comments, classes, imports, packages, methods);
    }
    
    
    public String remakeComments() {
        String temp = text;
        for (CommentBlock commentBlock : comments) {
            temp = temp.substring(0, commentBlock.getStart()) + commentBlock.text + temp.substring(commentBlock.getStart());
        }
        return temp;
    }

    public void removeInners() {
        List<Block> newBlocks = new ArrayList<Block>();
        for (Block block : blocks) {
            if (block instanceof CommentBlock || !block.isInnerInList(blocks)) {
                newBlocks.add(block);
            } else {
                System.out.println(">>>>>>>>>>>>>>" + text.substring(block.getStart(), block.getEnd()) + "<<<<<<<<<<<<<<<<");
            }
        }
        blocks = newBlocks;
    }

    public void add(int pos, String message, boolean hist) {
        if (hist) {
            history.add(pos, message.length());
        }
        text = text.substring(0, pos) + message + text.substring(pos);
        for (Block abstractBlock : blocks) {
            if (abstractBlock.getStart() >= pos) {
                abstractBlock.setStart(abstractBlock.getStart() + message.length());
            }
            if (abstractBlock.getEnd() >= pos) {
                abstractBlock.setEnd(abstractBlock.getEnd() + message.length());
            }
        }
    }

    public void remove(Block block, boolean remove, boolean hist) {
        if (hist) {
            history.remove(block.getStart(), block.length());
        }
        text = text.substring(0, block.getStart()) + text.substring(block.getEnd());

        List<Block> newBlocks = new ArrayList<Block>();
        for (Block abstractBlock : blocks) {
            if (abstractBlock != block) {
                if (abstractBlock.getStart() >= block.getStart()) {
                    abstractBlock.setStart(abstractBlock.getStart() - block.length());
                }
                if (abstractBlock.getEnd() >= block.getStart()) {
                    abstractBlock.setEnd(abstractBlock.getEnd() - block.length());
                }
                newBlocks.add(abstractBlock);
            }
        }
        if (remove) {
            blocks = newBlocks;
        }

    }

    public void move(Block block, int newPos) {
        if (!blocks.contains(block)) {
            blocks.add(block);
        }
        history.move(block.getStart(), newPos, block.length());
        String temp = text.substring(block.getStart(), block.getEnd());
        remove(block, false, false);
        add(newPos, temp, false);
        block.setEnd(newPos + block.length());
        block.setStart(newPos);
    }

    public int addIdent(Block block) {
        int result = 4;
        if (!blocks.contains(block)) {
            blocks.add(block);
        }
        add(block.getStart(), "    ", true);
        for (int i = block.getStart(); i < block.getEnd(); i++) {
            if (text.charAt(i) == '\n') {
                add(i + 1, "    ", true);
                result += 4;
            }
        }
        return result;
    }

    public int movePackage(int pos) {
        if (!packages.isEmpty()) {
            packages.get(0).getText().length();
            move(packages.get(0), pos);
            add(packages.get(0).length(), "\n", true);
            pos += packages.get(0).length();
        }
        return pos;
    }

    public int moveImports(int pos) {
        for (Import imp : imports) {
            move(imp, pos);
            pos += imp.length();
        }
        if (!imports.isEmpty()) {
            add(pos, "\n\n", true);
            pos += 2;
        }
        return pos;
    }

    public int moveClasses(int pos, String name) throws SyntaxException {
        
        for (int i = 0; i < classes.size(); i++) {
            Classe classe = classes.get(i);
            if (classe.getName().equals(name)) {
                if (classe.isInterface()) {
                    throw new SyntaxException("Main cannot be created on Interface", classe.getStart());
                }
                if (classe.isEnum() && classe.getEnumDefinition().isEmpty()) {
                    throw new SyntaxException("Enum enumeration is not defined", classe.getStart());
                }
                mainClass = classe;

            } else if (classe.isPublic()) {
                throw new SyntaxException("Public Class should be declared in a File with it name", classe.getStart());
            }
            move(classe, pos);
            pos += classe.length();
            if (i != classes.size() - 1 || mainClass == null) {
                add(pos, "\n\n", true);
                pos += 2;
            }
        }
        if (mainClass == null) {
            
            mainClass = new Classe(this, pos, 14+pos+name.length(), 18+pos+name.length());
            blocks.add(mainClass);
            add(pos, "public class " + name + " {\n\n}", true);
            mainClass.setEnd(pos + 18 + name.length());
            mainClass.setStart(pos);
            pos += mainClass.getText().length();
        }
        return pos;
    }

    private int createMainClass(String name, int position) {
//        add(position, "public class " + name + " {\n\n}", true);
//        mainClass = new Classe("public class " + name + " {\n\n}", 0, 14 + name.length(), 18 + name.length()); 
//        blocks.add(mainClass);
//        mainClass.setStartLength(position, mainClass.length());
//        position += 14 + name.length();
//        return posi[
        return -1;
        
    }

}
