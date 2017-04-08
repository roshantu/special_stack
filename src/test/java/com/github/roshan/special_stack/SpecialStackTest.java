package com.github.roshan.special_stack;

import org.junit.Test;

/**
 * Created by roshan on 2017/4/8.
 */
public class SpecialStackTest {
    @Test
    public void StackTest() {
        SpecialStack stack = new SpecialStack(10000);
        stack.push(1);
        stack.push(5);
        stack.push(8);
        stack.push(3);
        System.out.println(stack.top());
        System.out.println(stack.max());
        System.out.println(stack.min());
        System.out.println(stack.median());
        stack.pop();
        System.out.println(stack.top());
        System.out.println(stack.max());
        System.out.println(stack.min());
        System.out.println(stack.median());
    }
}
