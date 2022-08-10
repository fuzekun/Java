package compiler.nameCheckP;

import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import org.omg.CORBA.PUBLIC_MEMBER;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;
import javax.lang.model.util.ElementScanner8;
import javax.tools.Diagnostic;


import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.ElementScanner6;

import java.util.EnumSet;

import static javax.lang.model.element.ElementKind.*;
import static javax.lang.model.element.Modifier.*;
import static javax.tools.Diagnostic.Kind.WARNING;

/*
*
*   javac -processor compiler.NameCheckProcessor D:\projects\java\JVM_expriment\src\main\java\compiler\BADLY_NAMED_CODE.java
* 在target目录下执行这个命令就行了
* */
public class NameChecker {
    public static void main(String[] args) {

    }
    private final Messager messager;
    private NameCheckScanner nameCheckScanner = new NameCheckScanner();

    NameChecker(ProcessingEnvironment processingEnvironment) {
        messager = processingEnvironment.getMessager();
    }

    public void checkNames(Element element) {
        nameCheckScanner.scan(element);
    }

    private class NameCheckScanner extends ElementScanner6<Void, Void> {

        /*
        *
        *   对类检查是否符合驼峰命名
        * */
        @Override
        public Void visitType(TypeElement e, Void aVoid) {
            scan(e.getTypeParameters(), aVoid);
            checkCamelCase(e, true);
            return super.visitType(e, aVoid);
        }
        /*
        *
        *   对方法进行检查
        * */

        @Override
        public Void visitExecutable(ExecutableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.METHOD) {
                Name name = e.getSimpleName();
                if (name.contentEquals(e.getEnclosingElement().getSimpleName())) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "一个普通方法  " + name + " 不应该和类同名", e);
                }
                checkCamelCase(e, false);
            }

            return super.visitExecutable(e, aVoid);
        }

        /*
        *
        *   对变量进行检查
        * */

        @Override
        public Void visitVariable(VariableElement e, Void aVoid) {
            if (e.getKind() == ElementKind.ENUM_CONSTANT || e.getConstantValue() != null
            || heuristcallyConstant(e))
                checkAllCaps(e);
            else checkCamelCase(e, false);
            return super.visitVariable(e, aVoid);
        }

        /*
        *
        *   检查是否式常量
        * */
        private boolean heuristcallyConstant(VariableElement e) {
            if (e.getEnclosingElement().getKind() == ElementKind.INTERFACE) {
                // 如果式字面量也就是final，
                return true;
            } else if (e.getKind() == FIELD && e.getModifiers().containsAll(EnumSet.of(PUBLIC, STATIC, FINAL)))
                return true;
            return false;
        }

        /**
         * @param initialCaps true代表是一个类，false代表是一个方法
         * @param e 代表一个基本元素
         *
        */
        private void checkCamelCase(Element e, boolean initialCaps) {

            String name = e.getSimpleName().toString();
            boolean previousUpper = false;
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);
            if (Character.isUpperCase(firstCodePoint)) {
                previousUpper = true;
                if (!initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + " 应当以小写字母开头");
                    return;
                }
            } else if (Character.isLowerCase(firstCodePoint)) {
                if (initialCaps) {
                    messager.printMessage(Diagnostic.Kind.WARNING, "名称" + name + " 应当以大写字母开头", e);
                    return;
                }
            } else {
                conventional = false;
            }

            if (conventional) {
                int cp = firstCodePoint;
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (Character.isUpperCase(cp)) {
                        if (previousUpper) {
                            conventional = false;
                            break;
                        }

                        previousUpper = true;
                    } else {
                        previousUpper = false;
                    }
                }
            }

            if (!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "名称  " + name + " 应该符合驼峰命名法则", e);
            }
        }
        /**
         * 常量必须全部大写，或者使用下划线或者数字，但是必须以字母开头
         * 并且不允许连续的下划线
         * @param e 检查常量是否符合规范
         *
         * */

        private void checkAllCaps(Element e) {
            String name = e.getSimpleName().toString();
            boolean conventional = true;
            int firstCodePoint = name.codePointAt(0);   // 码点，不是代码单元哦
            if (!Character.isUpperCase(firstCodePoint))
                conventional = false;
            else {
                boolean previousUnderscore = false;
                int cp = firstCodePoint;
                // 遍历字符集
                for (int i = Character.charCount(cp); i < name.length(); i += Character.charCount(cp)) {
                    cp = name.codePointAt(i);
                    if (cp == (int) '_') {
                        if (previousUnderscore) {
                            conventional = false;
                            break;
                        }
                        previousUnderscore = true;
                    }
                    else {
                        previousUnderscore = false;
                        if (!Character.isUpperCase(cp) && !Character.isDigit(cp)) {
                            conventional = false;
                            break;
                        }
                    }
                }
            }
            if(!conventional) {
                messager.printMessage(Diagnostic.Kind.WARNING, "常量 " + name + "" +
                        "应该全部以大写字母或者下划线进行命名，并且以字母开头", e);
            }
        }
    }
}




