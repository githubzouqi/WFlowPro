package com.example.myapplication;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.myapplication.entity.EquitiesBean;
import com.example.myapplication.entity.Equitity;
import com.example.myapplication.entity.ListNode;
import com.example.myapplication.impl.HttpDns;
import com.example.myapplication.receiver.MyBroadCastReceiver;
import com.example.myapplication.util.MySystemProperties;
import com.example.myapplication.view.Loading;

import org.qiyi.basecore.taskmanager.TM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import dalvik.system.PathClassLoader;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends FragmentActivity {

    private TextView tv_data;
    private Loading loading;
    private String TAG = "imxiaoqi";
    private MyBroadCastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        testVolume();
//        testJson();
//        testComma();
//        testDate();
//        testInternet();
//        testLongParse();
//        testEncode();
//        testUrlParam();
//        testStartApp();
//        testUrlRecombine();
//        testShowLoading();
//        testContentResolve();
//        testGetPorp();
//        testIntentData();
//        testAlertDialogBgTrans();
//        testSpilt();
//        testGetClassLoader();
//        testReplaceStr();
//        testSendBroadcast();

//        goMain2();

//        cutNoodles();
//        isUnique("leetcode");
//        checkPermutation("", "");
//        replaceSpace("Mr John Smith    ", 13);
//        doTask();

        stack();
    }

    // 栈 - 先进后出
    private void stack() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(20);
        stack.push(13);
        stack.push(40);
        stack.push(38);

        System.out.println(":> peek: " + stack.pop());
    }



    /**
     * MinStack minStack = new MinStack();
     * minStack.push(-2);
     * minStack.push(0);
     * minStack.push(-3);
     * minStack.getMin();   --> 返回 -3.
     * minStack.pop();
     * minStack.top();      --> 返回 0.
     * minStack.getMin();   --> 返回 -2.
     */
    static class MinStack {

        Stack<Integer> stack;
        Stack<Integer> minStack;
        int min = Integer.MAX_VALUE;
        /** initialize your data structure here. */
        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);
            min = Math.min(x, min);
            minStack.push(min);
        }

        public void pop() {
            stack.pop();
            minStack.pop();
            if (minStack.empty()){
                min = Integer.MAX_VALUE;
            }else {
                min = minStack.peek();
            }
        }

        public int top() {
            int top = stack.peek();
            return top;
        }

        public int getMin() {
            return min;
        }
    }


    private void doTask() {
        /*
        System.out.println(":> postUI");
        // 提交任务到主线程
        TM.postUI(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    System.out.println(":> postUI sleep");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        */

        System.out.println(":> postAsync");
        // 提交任务到子线程
        TM.postAsync(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(":> postAsync sleep");
            }
        });

    }


    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode listNode = null;

        if (headA == null || headB == null){
            return null;
        }

        // 将 headA 转换为 集合对象 nodeAList
        List<Integer> nodeAList = new ArrayList<>();
        nodeAList.add(headA.val);
        ListNode nextA = headA.next;
        while (nextA != null){
            nodeAList.add(nextA.val);
            nextA = nextA.next;
        }


        // 将 headB 转换为 集合对象 nodeBList
        List<Integer> nodeBList = new ArrayList<>();
        nodeBList.add(headB.val);
        ListNode nextB = headB.next;
        while (nextB != null){
            nodeBList.add(nextB.val);
            nextB = nextB.next;
        }


        if (nodeAList.size() == 0 || nodeBList.size() == 0){
            return listNode;
        }

        int sizeA = nodeAList.size();
        int sizeB = nodeBList.size();
        int len = sizeA;
        List<Integer> list = nodeAList;
        List<Integer> listMax = nodeBList;
        if (sizeA > sizeB){
            len = sizeB;
            list = nodeBList;
            listMax = nodeAList;
        }

        boolean isContinue = true;
        int nodeValue = 0;
        for(int i = 0;i < len;i++){
            int num = list.get(i);
            if (listMax.contains(num)){
                isContinue = false;
                nodeValue = num;
                break;
            }
        }

        if (!isContinue){
            listNode = new ListNode(nodeValue);
        }

        return listNode;
    }

    /**
     * 检查输入的链表是否是回文的
     * [1,2,1]
     * [1,2,2,1]
     * [1,2,3,2,1]
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        boolean isPalindrome = false;
        List<Integer> list = new ArrayList<>();
        if (head != null){
            int val = head.val;
            list.add(val);
            ListNode next = head.next;
            while (next != null){
                int value = next.val;
                list.add(value);
                next = next.next;
            }

            if (list.size() == 1){
                isPalindrome = true;
            }else {
                StringBuilder builder1 = new StringBuilder("");
                StringBuilder builder2 = new StringBuilder("");
                int size = list.size();
                for (int i = 0;i < size;i++){
                    String s = String.valueOf(list.get(i));
                    String s2 = String.valueOf(list.get(size - 1 - i));
                    builder1.append(s);
                    builder2.append(s2);
                }

                if (builder1.toString().equals(builder2.toString())){
                    isPalindrome = true;
                }
            }

        }else {
            isPalindrome = true;
        }
        return isPalindrome;
    }

    /**
     * 给定两个用链表表示的整数，每个节点包含一个数位。
     * 这些数位是反向存放的，也就是个位排在链表首部。
     *
     * 编写函数对这两个整数求和，并用链表形式返回结果。
     * ===
     * 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
     * 输出：2 -> 1 -> 9，即912
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode mNode = null;
        if (l1 == null || l2 == null){
            return mNode;
        }

        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        int val1 = l1.val;
        list1.add(val1);
        ListNode node1 = l1.next;
        while (node1 != null){
            val1 = node1.val;
            list1.add(val1);
            node1 = node1.next;
        }

        int val2 = l2.val;
        list2.add(val2);
        ListNode node2 = l2.next;
        while (node2 != null){
            val2 = node2.val;
            list2.add(val2);
            node2 = node2.next;
        }

        while (list1.get(list1.size() - 1) == 0){
            if (list1.size() > 1){
                list1.remove(list1.size() - 1);
            }else {
                break;
            }
        }
        while (list2.get(list2.size() - 1) == 0){
            if (list2.size() > 1){
                list2.remove(list2.size() - 1);
            }else {
                break;
            }
        }

        int size1 = list1.size();
        int size2 = list2.size();
        if (size1 == 0 && size2 == 0){
            mNode = new ListNode(0);
            return mNode;
        }

        List<Integer> mList = new ArrayList<>();
        boolean needCarryBit = false;// 是否需要进位
        int len = size1;
        if (size1 < size2){
            len = size2;
        }
        // [2,4,3] [5,6,4]
        // [1] [9,9,9,9,9,9,9,9]
        // [9,9,9,9,9,9,9,9] [1]
        for (int pos = 0;pos < len;pos++){
            int value1 = 0;
            int value2 = 0;
            if (pos < size1){
                value1 = list1.get(pos);
            }
            if (pos < size2){
                value2 = list2.get(pos);
            }
            int value = value1 + value2;
            if (needCarryBit){
                value += 1;
            }

            if (value > 9){
                needCarryBit = true;
            }else {
                needCarryBit = false;
            }

            if (needCarryBit){
                int tempValue = value % 10;
                mList.add(tempValue);

                if (pos == len - 1){
                    mList.add(1);
                }

            }else {
                mList.add(value);
            }
        }

        // 807=[7,0,8]
        int mSize = mList.size();
        for (int i = mSize - 1;i >= 0;i--){
            int value = mList.get(i);
            if (i == mSize - 1){
                mNode = new ListNode(value);
            }else {
                ListNode node = new ListNode(value);
                node.next = mNode;
                mNode = node;
            }
        }

        return mNode;
    }

    /**
     * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在
     * 大于或等于 x 的节点之前。
     *
     * 你不需要 保留 每个分区中各节点的初始相对位置。
     * ===
     * 输入：head = [1,4,3,2,5,2], x = 3
     * 输出：[1,2,2,4,3,5]
     * ===
     * 提示：
     * 链表中节点的数目在范围 [0, 200] 内
     * -100 <= Node.val <= 100
     * -200 <= x <= 200
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        List<Integer> listBigger = new ArrayList<>();
        List<Integer> listSmaller = new ArrayList<>();
        ListNode mNode = null;

        if (x <= -100 || x >= 100){
            return head;
        }

        // 不存在节点或者只存在一个节点的情况
        if (head == null || head.next == null){
            return head;
        }

        int val = head.val;
        if (val >= x){
            listBigger.add(val);
        }else {
            listSmaller.add(val);
        }

        ListNode nextNode = head.next;
        while (nextNode != null){
            val = nextNode.val;
            if (val >= x){
                listBigger.add(val);
            }else {
                listSmaller.add(val);
            }
            nextNode = nextNode.next;
        }

        // final
        if (listBigger.size() == 0 || listSmaller.size() == 0){
            return head;
        }

        for (int i = 0;i < listBigger.size();i++){
            int value = listBigger.get(i);
            listSmaller.add(value);
        }

        int size = listSmaller.size();
        for (int pos = (size - 1);pos >= 0;pos--){
            int mValue = listSmaller.get(pos);
            if (pos == size - 1){
                mNode = new ListNode(mValue);
            }else{
                ListNode node = new ListNode(mValue);
                node.next = mNode;
                mNode = node;
            }
        }

        return mNode;
    }

    /**
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     *
     * 找出单向链表中倒数第 k 个节点。返回该节点的值
     * 给定的 k 保证是有效的。
     *
     * 输入： 1->2->3->4->5 和 k = 2
     * 输出： 4
     */
    public int kthToLast(ListNode head, int k) {
        int kValue = 0;

        List<Integer> mList = new ArrayList<>();
        if (head != null){
            int val = head.val;
            ListNode nextNode = head.next;

            mList.add(val);

            if (nextNode == null){
                return val;
            }

            while (nextNode != null){
                int value = nextNode.val;
                mList.add(value);
                nextNode = nextNode.next;
            }

            // 获取集合中到数第k个的值
            int size = mList.size();
            kValue = mList.get(size - k);

        }

        return kValue;
    }

    /**
     * 移除未排序链表中的重复节点。保留最开始出现的节点
     * 链表长度在[0, 20000]范围内。
     * 链表元在[0, 素20000]范围内。
     * Definition for singly-linked list.
     * public class ListNode {
     *     int val;
     *     ListNode next;
     *     ListNode(int x) { val = x; }
     * }
     * ===
     *  输入：[1, 2, 3, 3, 2, 1]
     *  输出：[1, 2, 3]
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        ListNode mNode = null;
        List<Integer> mList = new ArrayList<>();
        if (head != null){
            ListNode nextNode = head.next;
            int val = head.val;

            // 长度为 1
            if (nextNode == null){
                return head;
            }

            mList.add(val);
            while (nextNode != null){
                int tempVal = nextNode.val;
                if (!mList.contains(tempVal)){
                    mList.add(tempVal);
                }
                nextNode = nextNode.next;
            }


            int size = mList.size();
            for (int i = size - 1;i >= 0;i--){
                int value = mList.get(i);
                ListNode node = new ListNode(value);
                if (i == size - 1){
                    mNode = new ListNode(value);
                }else {
                    node.next = mNode;
                    mNode = node;
                }
            }


        }

        return mNode;
    }


    /**
     * 字符串轮转。给定两个字符串s1和s2，请编写代码检查s2是否为s1旋转而成
     * （比如，waterbottle是erbottlewat旋转后的字符串）。
     * 字符串长度在[0, 100000]范围内。
     * @param s1
     * @param s2
     * @return
     */
    public boolean isFlipedString(String s1, String s2) {
        boolean isFliped = false;

        int len1 = s1.length();
        int len2 = s2.length();

        if (len1 == len2){
            if (len1 == 0){
                return true;
            }
            if (s1.equals(s2)){
                if (len1 > 1){
                    return false;
                }
                return true;
            }else {

                // "waterbottle" "erbottlewat"

                for (int i = 0;i < len1 - 1;i++){
                    String lastS = String.valueOf(s2.charAt(len2 - 1));

                    if (!s1.contains(lastS)){
                        isFliped = false;
                        break;
                    }

                    // abc
                    String subStr1 = s2.substring(0, len2 - 1);
                    s2 = lastS + subStr1;

                    if (s1.equals(s2)){
                        isFliped = true;
                        break;
                    }
                }
            }

        }

        return isFliped;
    }

    /**
     * 若M × N矩阵中某个元素为0，则将其所在的行与列清零。
     * @param matrix
     */
    public void setZeroes(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;

        int [][] mMatrix = new int[row][col];
        List<Integer> rowList = new ArrayList<>();
        List<Integer> colList = new ArrayList<>();

        for (int i = 0;i < row;i++){
            for (int j = 0;j < col;j++){
                int value = matrix[i][j];
                // value is 0
                if (value == 0){
                    if (!rowList.contains(i)){
                        rowList.add(i);
                    }
                    if (!colList.contains(j)){
                        colList.add(j);
                    }
                }
            }
        }

        for (int i = 0;i < row;i++){
            for (int j = 0;j < col;j++){
                if (rowList.contains(i) || colList.contains(j)){
                    matrix[i][j] = 0;
                }
            }
        }

    }

    /**
     * 旋转矩阵
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        int len = matrix.length;
        int[][] mMatrix = new int[len][len];
        for (int i = 0;i < len;i++){
            for (int j = 0;j < len;j++){
                mMatrix[i][j] = matrix[len -1 -j][i];
            }
        }

        for (int i = 0;i < len;i++){
            for (int j = 0;j < len;j++){
                matrix[i][j] = mMatrix[i][j];
            }
        }
    }

    /**
     * 字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能
     * 比如，字符串aabcccccaaa会变为a2b1c5a3。若“压缩”后的字符串没有变短，则返回原先的字符串
     * 你可以假设字符串中只包含大小写英文字母（a至z）
     * @param S
     * @return
     */
    public String compressString(String S) {
        String mStr = S;
        if (S != null){
            int len = mStr.length();
            if (len == 0 || len == 1){
                return S;
            }

            // aa aA
            int count = 0;
            StringBuilder builder = new StringBuilder("");
            for (int i = 0;i < len;i++){
                char c = S.charAt(i);
                String str = builder.toString();
                if (str.equals("")){
                    builder.append(c);
                    count++;
                }else {
                    if (str.charAt(str.length() - 1) == c){
                        count++;
                        // last
                        if (i == len - 1){
                            builder.append(count);
                        }
                    }else {
                        builder.append(count);
                        builder.append(c);
                        count = 1;
                        // last
                        if (i == len - 1){
                            builder.append(count);
                        }
                    }
                }
            }

            int newLen = builder.toString().length();
            if (newLen < len){
                return builder.toString();
            }

        }
        return mStr;
    }

    /**
     * 【一次编辑】
     * 字符串有三种编辑操作:插入一个英文字符、删除一个英文字符或者替换一个英文字符。
     * 给定两个字符串，编写一个函数判定它们是否只需要一次(或者零次)编辑
     * @param first pale
     * @param second ple
     * @return true
     */
    public boolean oneEditAway(String first, String second) {
        boolean isOneEdit = false;
        if (first != null && second != null){
            int len01 = first.length();
            int len02 = second.length();

            if (len01 == len02){
                if (first.equals(second)){
                    return true;
                }

                if (len01 == 1){
                    return true;
                }

                // ab ac
                for (int i = 0;i < len01;i++){
                    char c01 = first.charAt(i);
                    char c02 = second.charAt(i);
                    if (c01 != c02){
                        if (i == 0){
                            first = first.substring(1, len01);
                            second = second.substring(1, len02);
                        } else if (i == len01 -1){
                            first = first.substring(0, i);
                            second = second.substring(0, i);
                        } else {
                            first = first.substring(0, i) + first.substring(i + 1, len01);
                            second = second.substring(0, i) + second.substring(i + 1, len02);
                        }

                        if (first.equals(second)){
                            isOneEdit = true;
                        }
                        break;
                    }
                }

            }

            // 长度差一
            if (Math.abs(len01 - len02) == 1){
                if (first.equals("") || second.equals("")){
                    return true;
                }

                if (len01 > len02){
                    for (int i = 0;i < len02;i++){
                        char firstC = first.charAt(i);
                        char secondC = second.charAt(i);
                        if (firstC != secondC){
                            if (i == 0){
                                first = first.substring(1, len01);
                            }else {
                                first = first.substring(0, i) + first.substring(i + 1, len01);
                            }
                            break;
                        }

                        if (i == len02 -1){
                            return true;
                        }
                    }
                    if (first.equals(second)){
                        isOneEdit = true;
                    }
                }else {
                    for (int j = 0;j < len01;j++){
                        char firstC = first.charAt(j);
                        char secondC = second.charAt(j);
                        if (firstC != secondC){
                            if (j == 0){
                                second = second.substring(1, len02);
                            }else {
                                second = second.substring(0, j) + second.substring(j + 1, len02);
                            }
                            break;
                        }

                        if (j == len01 -1){
                            return true;
                        }
                    }
                    if (first.equals(second)){
                        isOneEdit = true;
                    }
                }

            }

        }
        return isOneEdit;
    }

    // 回文排列
    public boolean canPermutePalindrome(String s) {
        boolean isHw = false;
        // 统计每个字符的个数，出现大于 1 次的奇数个字符
        if (s != null && !s.equals("")){
            int len = s.length();
            if (len == 1) return true;
            if (len == 2 && (s.charAt(0) == s.charAt(1))){
                return true;
            }

            Map<String, Object> map = new HashMap<>();
            // 循环统计各个字符出现的次数
            for (int i = 0;i < len;i++){
                String mKey = String.valueOf(s.charAt(i));
                int no = 1;
                if (map.containsKey(mKey)){
                    no = Integer.parseInt(String.valueOf(map.get(mKey)));
                    no++;
                }
                map.put(mKey, no);
            }
            
            // 奇数个数出现超过 1 次就
            Iterator iterator = map.keySet().iterator();
            int sum = 0;
            while (iterator.hasNext()){
                Object value = map.get(iterator.next());
                int count = Integer.parseInt(String.valueOf(value));
                if (count % 2 != 0){
                    // 奇数
                    sum++;
                }
            }

            if (sum <= 1) {
                return true;
            }
        }

        return isHw;
    }

    // URL 化
    public String replaceSpace(String S, int length) {
        StringBuilder builder = new StringBuilder("");
        if (length > 0){
            int mLen = S.length();
            if (length == mLen){
                S = S.replaceAll(" ", "%20");
                return S;
            }
            if (length < mLen){
                S = S.substring(0, length);
                S = S.replaceAll(" ", "%20");
                return S;
            }
            if (length > mLen){
                S = S.replaceAll(" ", "%20");
                for (int i = 0;i < mLen - length;i++){
                    builder.append(S).append("%20");
                }
            }
        }
        return builder.toString();
    }

    // 字符重排
    public boolean checkPermutation(String s1, String s2) {
        boolean isPermutate = false;
        int len1 = s1.length();
        int len2 = s2.length();
        if (len1 == 0 || len2 == 0){
            return isPermutate;
        }

        if (len1 == len2){
            char[] chars1 = str2Array(s1);
            char[] chars2 = str2Array(s2);
            Arrays.sort(chars1);
            Arrays.sort(chars2);
            if (Arrays.equals(chars1, chars2)){
                isPermutate = true;
            }
        }

        return isPermutate;
    }

    // string 转 数组
    public char[] str2Array(String s){
        char[] chars = {};
        if (!s.equals("")){
            int len = s.length();
            chars = new char[len];
            for (int i = 0;i < len;i++){
                char mChar = s.charAt(i);
                chars[i] = mChar;
            }
        }
        return chars;
    }


    public boolean isUnique(String astr) {
        boolean isUnique = true;
        String newAstr02 = "",newAstr01 = "",c;
        if (!astr.equals("")){
            int len = astr.length();
            if (len == 1) return true;
            for (int i = 0; i < len; i++){
                if (i == len - 1){
                    isUnique = true;
                    break;
                }

                newAstr02 = astr.substring(i+1, len);
                c = String.valueOf(astr.charAt(i));

                if (i == 0){
                    if (newAstr02.contains(c)){
                        isUnique = false;
                        break;
                    }
                }

                if (i > 0){
                    newAstr01 = astr.substring(0, i);
                    if (newAstr01.contains(c) || newAstr02.contains(c)){
                        isUnique = false;
                        break;
                    }
                }

            }
        }
        return isUnique;
    }

    // 切面条
    private void cutNoodles() {
        /**
         * 不对折 0 -> 2
         * 对折一次 1 -> 2 + 1
         * 对折两次 2 -> 2 + 1 + 2
         */

        double sum = 2;
        for(int i = 0;i < 10;i++){
            sum = sum + Math.pow(2, i);
        }
        System.out.println(":> sum = " + sum);

    }

    private void goMain2() {
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
        finish();
    }


    /**
     *  二进制中1的个数
     *  输入必须是长度为 32 的 二进制串
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        int num = 0;
        String s = n + "";

        s = "00000000000000000000000000001011";
        // case 3
        int length = s.length();
        for (int i = 0;i < length;i++){
            String sub = String.valueOf(s.charAt(i));
            if (sub.equals("1")){
                num++;
            }
        }
        System.out.println(":> num: " + num);

        return num;
    }

    /**
     * 二进制手表
     * 8 4 2 1
     * 32 16 8 4 2 1
     * @param turnedOn 表示当前亮着的 LED 的数量 0 <= turnedOn <= 10
     * @return
     */
    public List<String> readBinaryWatch(int turnedOn) {
        List<String> timeList = new ArrayList<>();
        if (turnedOn == 0){
            timeList.add("0:00");
            return timeList;
        }
        if (turnedOn < 10){

        }
        return timeList;
    }

    public List<String> getHour(int num){
        List<String> hList = new ArrayList<>();
        if (num == 0){
            hList.add("0");
            return hList;
        }
        if (num == 1){
            hList.add("1");
            hList.add("2");
            hList.add("4");
            hList.add("8");
            return hList;
        }
        if (num == 2){
            hList.add("3");
            hList.add("5");
            hList.add("9");
            hList.add("6");
            hList.add("10");
            hList.add("12");
            return hList;
        }
        if (num == 3){
            hList.add("7");
            hList.add("11");
            hList.add("14");
            return hList;
        }
        if (num == 3){
            hList.add("15");
            return hList;
        }
        return hList;
    }

    private void testSendBroadcast() {
//        if (mReceiver == null){
//            mReceiver = new MyBroadCastReceiver();
//        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("com.iflytek.xiri.action.EPG.openurl");
        Intent intent = new Intent();
        String open_url = "http://www.baidu.com";
        intent.setAction("com.iflytek.xiri.action.EPG.openurl");
        intent.putExtra("open_url", open_url);
        sendBroadcast(intent);
//        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null){
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    private void testReplaceStr() {
        String url = "http://www.baidu.com?clientId=undefined&name=zq&age=26&money=&hobby=undefined";
        String url2 = "http://www.baidu.com?clientId=&name=zq2&age=262&money=2&hobby=undefined2";
        String clientId = Uri.parse(url).getQueryParameter("clientId");
        String clientId2 = Uri.parse(url2).getQueryParameter("clientId");
        String new_url = url.replaceFirst("clientId=" + clientId, "clientId=imxiaoqi");
        String new_url2 = url2.replaceFirst("clientId=" + clientId2, "clientId=imxiaoqi");
        System.out.println(":> new url: " + new_url);
        System.out.println(":> new url2: " + new_url2);
    }

    /**
     * 山脉数组的峰顶索引
     * arr.length >= 3
     *
     * 3 <= arr.length <= 104
     * 0 <= arr[i] <= 106
     * 题目数据保证 arr 是一个山脉数组
     * @param arr
     * @return
     */
    public int peakIndexInMountainArray(int[] arr) {
        int index = 0;
//        List<Integer> temp = Arrays.stream(arr).boxed().collect(Collectors.toList());

//        System.out.println(":> temp 0: " + temp.get(0));
        Arrays.sort(arr);// 升序
        int max = arr[arr.length - 1];
        System.out.println(":> max of arr is: " + max);
//        index = temp.indexOf(max);
        return index;
    }

    /**
     * 连续子数组
     * 1、 子数组大小 至少为 2
     * 2、子数组元素总和为 k 的倍数
     * 如果存在一个整数 n ，令整数 x 符合 x = n * k ，则称 x 是 k 的一个倍数。
     * @param nums [23,2,4,6,7]
     * @param k 6
     * @return
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        boolean canMod = false;
        int len = nums.length;

        // case 1
        if (len == 1){
            return false;
        }

        // case 2
        if (k == 1){
            return true;
        }

        boolean isContinue = true;
        // other case
        for(int num = 2;num <= len;num++){
            if (isContinue){
                for (int i = 0;i< len - num + 1;i++){
                    long sum = getSum(nums, i, num);
                    if (sum % k == 0){
                        isContinue = false;
                        canMod = true;
                        break;
                    }
                }
            }else {
                break;
            }
        }

        return canMod;
    }

    public long getSum(int[] nums, int i, int num) {
        long sum = 0;
        for (int j = i;j < i + num;j++){
            sum += nums[j];
        }
        return sum;
    }

    private void testGetClassLoader() {
        // 类加载层级 : BootClassLoader -> PathClassLoader/DexClassLoader
        ClassLoader loader = getClassLoader();
        ClassLoader parent = loader.getParent();
        ClassLoader pParent = parent.getParent();
        System.out.println(":> loader: " + loader);
        System.out.println(":> parent: " + parent);
        System.out.println(":> pParent: " + pParent);

    }

    /**
     *  1744. 你能在你最喜欢的那天吃到你最喜欢的糖果吗？
     *  输入：candiesCount = [7,4,5,3,8], queries = [[0,2,2],[4,2,4],[2,13,1000000000]]
     * @param candiesCount
     * @param queries
     * @return
     */
    public boolean[] canEat(int[] candiesCount, int[][] queries) {
        // 求前缀和
        long[] preSum = new long[candiesCount.length];
        long preSums = 0;
        for (int i = 0;i < candiesCount.length;i++){
            preSum[i] = preSums;
            preSums += candiesCount[i];
        }
        int len = queries.length;
        boolean[] eat = new boolean[len];
        for(int i = 0;i < len;i++){
            int day = queries[i][1];// 第几天 (0天开始吃)
            int cap = queries[i][2];// 每天最多能吃的candy
            int type = queries[i][0];// 第几类candy
            long dayMin = day + 1;// 第 day 天最少吃的candy
            long dayMax = (long)day * cap + cap;// 第 day 天最多吃的candy 注意：这里需要考虑溢出的情况

            // 判断是否有交集喔
            if ((preSum[type] + 1 <= dayMax) && (preSum[type] + candiesCount[type] >= dayMin)){
                eat[i] = true;
            }else {
                eat[i] = false;
            }

        }
        return eat;
    }

    /**
     *  输入：nums = [2,7,11,15], target = 9
     *  输出：[0,1]
     *  解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] twoNum = new int[2];
        int len = nums.length;
        boolean isContinue = true;
        for (int i = 0;i < len;i++){
            if (!isContinue){
                break;
            }
            if (i == len - 1){
                break;
            }
            int first = nums[i];
            for (int j = i+1;j < len;j++){
                int second = nums[j];
                if (target == (first + second)){
                    twoNum[0] = i;
                    twoNum[1] = j;
                    isContinue = false;
                    break;
                }
            }
        }
        return twoNum;
    }

    /**
     * 给定一个整数，写一个函数来判断它是否是 4 的幂次方。如果是，返回 true ；否则，返回 false 。
     * 整数 n 是 4 的幂次方需满足：存在整数 x 使得 n == 4^x
     * @param n
     * @return
     */
    public boolean isPowerOfFour(int n) {
        boolean isPower = false;
        // case 1
        if (n == 1 || n == 4){
            return true;
        }
        while (n > 4 && n % 4 == 0){
            n = n / 4;
        }
        if (n == 4){
            isPower = true;
        }
        return isPower;
    }

    /**
     *  两个整数的 汉明距离 指的是这两个数字的二进制数对应位不同的数量。
     *  计算一个数组中，任意两个数之间汉明距离的总和。
     * @param nums
     * @return
     */
    public int totalHammingDistance(int[] nums) {
        int count = 0;
        return count;
    }

    /**
     * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
     * 0 ≤ x, y < 2^31.
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance(int x, int y) {
        int count = 0;
        int z = x ^ y;// 异或运算
        count = Integer.bitCount(z);
        return count;
    }

    /**
     * 给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     *
     * [0,2,3]
     * @param nums
     * 必须在原数组上操作，不能拷贝额外的数组
     */
    public void moveZeroes(int[] nums) {
        int len = nums.length;
        if (len > 1){
            int i = 0;
            int temp;
            while (i < len){
                for (int j = 0;j < len - i - 1;j++){
                    if (nums[j] == 0){
                        // 0 往后移动
                        temp = nums[j];
                        nums[j] = nums[j + 1];
                        nums[j + 1] = temp;
                    }
                }

                i++;
            }
        }
    }

    /**
     * 字符串 s（仅含有小写英文字母和括号）
     * 请你按照从括号内到外的顺序，逐层反转每对匹配括号中的字符串，并返回最终的结果
     * 注意，您的结果中 不应 包含任何括号。
     * @param s
     * @return
     */
    public String reverseParentheses(String s) {
        return "";
    }

    /**
     * 翻转字符串内容
     * @param s
     * @return
     */
    public String reverseStr(String s){
        String str = "";
        if (s != null && s.length() > 0){
            StringBuilder builder = new StringBuilder("");
            int len = s.length();
            for(int i = len;i > 0;i--){
                builder.append(s.substring(i - 1, i));
            }
            str = builder.toString();
        }
        return str;
    }

    /**
     * 0 <= digits[i] <= 9
     * 1 <= digits.length <= 100
     * @param digits
     * @return
     */
    public int[] plusOne(int[] digits) {
        int len = digits.length;

        // case 1
        if (len == 1 && digits[0] == 0){
            digits[0] = 1;
            return digits;
        }

        // case 2
        if (digits[len - 1] < 9){
            digits[len - 1] += 1;
            return digits;
        }

        // 判断元素是否都是 9
        boolean isAll9 = true;
        for (int i = 0;i < len;i++){
            int element = digits[i];
            if (element != 9){
                isAll9 = false;
                break;
            }
        }

        if (isAll9){
            // 都是 9 的情况
            int[] digitsPlus = new int[len + 1];
            for (int i = 0;i < len + 1;i++){
                if (i == 0){
                    digitsPlus[i] = 1;
                }else {
                    digitsPlus[i] = 0;
                }
            }
            return digitsPlus;
        }else {
            // 不全是9的情况
            for (int i = len - 1;i >= 0;i--){
                if (digits[i] == 9){
                    digits[i] = 0;
                }else {
                    digits[i] += 1;
                    break;
                }
            }
            return digits;
        }
    }

    /**
     * LeetCode 2021-05-21 每日一题 :> 不相交的线
     * @param nums1 [1,2,2,2,4]
     * @param nums2 [2,4,1,2,4]
     * @return 解题思路-转化为求两数组的最长公共子序
     */
    public int maxUncrossedLines(int[] nums1, int[] nums2){
        int maxLines = 0;
        int len1 = nums1.length;
        int len2 = nums2.length;

        if (len1 == 1 && len2 == 1){
            if (nums1[0] == nums2[0]){
                maxLines = 1;
            }
            return maxLines;
        }

        if (len1 == 1 && len2 > 1){
            int index = 0;
            while (index < len2){
                if (nums1[0] == nums2[index]){
                    maxLines = 1;
                    break;
                }
                index++;
            }
            return maxLines;
        }

        if (len2 == 1 && len1 > 1){
            int index = 0;
            while (index < len1){
                if (nums2[0] == nums1[index]){
                    maxLines = 1;
                    break;
                }
                index++;
            }
            return maxLines;
        }

        // final case
        if (len1 > 1 && len2 > 1){

        }

        return maxLines;
    }

    private void testSpilt() {
        String msg = "/zx/yanhuajgTVzx/vod/pjsag003300100000000000000035811/mjsag003300100000000000000035811";
        String[] urls = msg.split("___");
        Log.e(TAG, ":> length = " + urls.length + ", urls[0] = " + urls[0]);
    }

    /**
     * 弹出对话框时背景透明
     */
    private void testAlertDialogBgTrans() {
        // 创建 Dialog 对象
        Dialog dialog = new Dialog(this, R.style.trans_bg_dialog);
        // 构建自定义 view
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_view_simple, null);
        // dialog 设置自定义内容试图 view
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        // 获取屏幕宽高
        WindowManager manager = window.getWindowManager();
        Display display = manager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        // 设置 dialog 显示宽高
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;// 自适应，实际可自行定义
        params.width = w * 720 / 1280;// 实际可自定义
        params.gravity = Gravity.CENTER;// 对话框居中
        window.setAttributes(params);
        // 显示 dialog
        dialog.show();
    }

    private void  testIntentData() {

        System.out.println("> --- testIntentData ---");
        Intent intent = getIntent();
        String action = "", type = "", url = "";
        if (intent != null){
            System.out.println("> intent not null");
            if (intent.hasExtra("action")){
                action = intent.getStringExtra("action");
            }
            if (intent.hasExtra("type")){
                type = intent.getStringExtra("type");
            }
            if (intent.hasExtra("url")){
                url = intent.getStringExtra("url");
                System.out.println("> 编码 url: " + url);
                url = URLDecoder.decode(url);
                System.out.println("> 解码 url：" + url);
            }

            System.out.println("> action: " + action + ", type: " + type
            + ", url: " + url);
            return;
        }

        System.out.println("> intent is null");
    }

    private void testGetPorp() {

        String modelName = MySystemProperties.get("ro.product.odm.model");
        Log.e(TAG, "> modelName: " + modelName);

    }

    private void testContentResolve() {

        Log.e("TAG", "> testContentResolve");
        Uri uri = Uri.parse("content://stbauthinfo/authentication");
//        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        ContentResolver resolver = getApplicationContext().getContentResolver();

        try {
            Cursor cursor = resolver.query(uri, null, null, null, null);
            if (cursor != null){
                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String value = cursor.getString(cursor.getColumnIndex("value"));
                    Log.e("TAG", "> name = " + name + ", value = " + value + "\n");
                }
            }else {
                Log.e("TAG", "> cursor is null");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("TAG", "> exception = " + e.getMessage());
        }

    }

    // 测试gif图loading
    private void testShowLoading() {

        loading = new Loading(this);

        showLoading();

    }

    private void showLoading(){
        if (loading == null || loading.isShowing() || isFinishing()) {
            return;
        }

        loading.show();
    }

    // 测试url参数重组
    private void testUrlRecombine() {
        String url = "http://js.fb_proxy.cmcc.launcher.gitv.tv/v1/feedback?userid=86d90211d7287d561dbd0e1f281dc5ff&itemid=04|23010012020120916470072848&type=play&trace_id=v2_7_1_1_86d90211d7287d561dbd0e1f281dc5ff_1611110753494_017fade70a72a2e151_4_0&signal=4&pos=5&record_albumid=&userisMd5=1&operate_time=&oriDomain=aHR0cDovL2RtcHJlYy5jbXJpLmNuOjgwODQ=";
        Uri uri  = Uri.parse(url);
        Set<String> params = uri.getQueryParameterNames();
        Iterator<String> iterator = params.iterator();
        StringBuilder paramBuilder = new StringBuilder();
        while (iterator.hasNext()){
            String key = iterator.next();
            String value = uri.getQueryParameter(key);
            Log.e("TAG", "> 获取 key = " + key + ", value = " + value);
            if (key.equals("userid")){
                value = "1008611";
            }
            paramBuilder.append(key + "=" + value + "&");
        }
        String recombineParam = paramBuilder.toString().substring(0, paramBuilder.toString().length() - 1);
        Log.e("TAG", "> 重新拼接后的 recombineParam = " + recombineParam);
        String path = uri.getPath();
        Log.e("TAG", "> path = " + path);
        String[] url_arrays = url.split(path);
        String newUrl = url_arrays[0] + path + "?" + recombineParam;
        Log.e("TAG", "> newUrl = " + newUrl);
    }

    private void testStartApp() {

        try {
//            String packageName = "com.android.tv.settings";
//            String className = "com.android.tv.settings.MainSettings";

            String packageName = "com.android.settings";
            String className = "com.android.settings.Settings";

            startAppByCn(packageName, className);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void startAppByCn(String packageName, String className){
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ComponentName componentName = new ComponentName(packageName, className);
        intent.setComponent(componentName);
        startActivity(intent);
    }

    // 测试获取url的参数
    private void testUrlParam() {

        String url_1 = "http://183.207.248.14:80/88890689/16/20201123/280787280/29-1-3.hls.ts?cdncode=/18907E7BE0798990/&HW_PERF=1-300-1.ts,10.000,10533076,5050996&cost=low";
        Uri uri = Uri.parse(url_1);
        String cdncode_1 = uri.getQueryParameter("cdncode");
        String HW_PERF_1 = uri.getQueryParameter("HW_PERF");
        String cost_1 = uri.getQueryParameter("cost");
        Log.w("TAG", "> url_1 : " + url_1);
        Log.w("TAG", "> cdncode_1:" + cdncode_1 + ", HW_PERF_1:" + HW_PERF_1
        + ", cost_1:" + cost_1);

        String url_2 = "http://183.207.248.14/88890689/16/20201123/280787280/29-1-3.hls.ts?HW_PERF=1-300-1.ts,10.000,10533076,5050996&cost=low&cdncode=/18907E7BE0798990/";
        Uri uri2 = Uri.parse(url_2);
        String cdncode_2 = uri2.getQueryParameter("cdncode");
        String HW_PERF_2 = uri2.getQueryParameter("HW_PERF");
        String cost_2 = uri2.getQueryParameter("cost");
        Log.w("TAG", "> url_2 : " + url_2);
        Log.w("TAG", "> cdncode_2:" + cdncode_2 + ", HW_PERF_2:" + HW_PERF_2
                + ", cost_2:" + cost_2);

        System.out.println("========");

        String url_bool = "http://192.168.1.120/demo?asset=1234567&subsetFree=true";
        Uri uri_bool = Uri.parse(url_bool);
        String subsetFree = uri_bool.getQueryParameter("subsetFree");
        if ("true".equals(subsetFree)){
            System.out.println(":> " + subsetFree);
        }
//        boolean subsetFree = uri_bool.getBooleanQueryParameter("subsetFree", false);
        System.out.println(":> subsetFree: " + subsetFree);
        String productId = "";
        productId = uri_bool.getQueryParameter("productId");
        System.out.println(":> productId: " + productId);

        System.out.println(":> =========");
        String playUrl = "yanhua://epg/play?playUrl=tencent%3A%2F%2Fsdk%2Fplayurl%3FclientId%3Dundefined%26vid%3Dt0036kis46j%26cdnUrl%3Dhttp%253A%252F%252Fcdnrrs.gz.chinamobile.com%253A80%252F88888888%252F16%252F20210605%252F269389570%252Findex.m3u8%253Frrsip%253Dcdnrrs.gz.chinamobile.com%253A80%2526startTime%253D0%2526zoneoffset%253D480%2526servicetype%253D0%2526icpid%253D115%2526accounttype%253D1%2526limitflux%253D-1%2526limitdur%253D-1%2526tenantId%253D8601%2526accountinfo%253D%25252C10500000001610%25252C117.188.10.108%25252C20210609195650%25252CYAN0010880826CIDmzc00200c6aodyr%25252C10001000052139%25252C-1%25252C1%25252C0%25252C%25252C1%25252C1%25252C0%25252C%25252C400926101%25252C1%25252C%25252CEND%2526GuardEncType%253D2%26historyTime%3D25000%26productId%3D%26subsetFree%3Dtrue";
        Uri uri_playurl = Uri.parse(playUrl);
        String playUrlValue = uri_playurl.getQueryParameter("playUrl");
        System.out.println(":> playUrlValue: " + playUrlValue);

        System.out.println(":> historyTime: " + Uri.parse(playUrlValue).getQueryParameter("historyTime"));

    }

    private void testEncode() {

//        String s = "p1=abc/123&p2?=dfg/123";
//
//        s = s.replaceAll("/", "%2f")
//                .replaceAll("=","%3D")
//        .replaceAll(Pattern.quote("?"),"%3F");
//        System.out.println(":> new s: " + s);

        String cdnUrl= "http://www.baidu.com/path?key1=v1&key2=v2/345&key3=v3";
//        Uri uri = Uri.parse(cdnUrl);
//        String path = uri.getPath();
        String path = cdnUrl.replace("&key3=v3", "");
        System.out.println(":> path = " + path);

    }

    private void testLongParse() {

        String time = "93771.99999999999";
        BigDecimal decimal = new BigDecimal(time);
//        BigDecimal decimal2 = new BigDecimal("0.5");
//        BigDecimal sum = decimal.add(decimal2);
        BigDecimal result = decimal.setScale(0, BigDecimal.ROUND_HALF_UP);
        long l_time = Long.parseLong(String.valueOf(result));
        Log.e("TAG", "> decimal = " + decimal);
//        Log.e("TAG", "> decimal2 = " + decimal2);
//        Log.e("TAG", "> sum = " + sum);
        Log.e("TAG", "> result = " + result);
        Log.e("TAG", "> l_time = " + l_time);

    }

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /*
    private void testInternet() {

        OkHttpClient client = new OkHttpClient.Builder().dns(new HttpDns()).build();
//        String url = "http://47.110.20.65/ums/vui?s=21&v=3&clientId=54002000021&spCode=1002&userName=&mac=002468eb638e";
        String url1 = "http://ums-i.yanhuamedia.tv/ums/vui?s=21&v=3&clientId=54002000021&spCode=1002&userName=&mac=002468eb638e";
//        final Request request = new Request.Builder().url(url).build();
        final Request request1 = new Request.Builder()
                .url(url1).build();
        final long t3 = System.currentTimeMillis();
        Log.e("TAG", "> ums-i.yanhuamedia.tv 接口请求时间：" + format.format(new Date(t3)));
        client.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call,  IOException e) {
                Log.e("TAG", "> onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse( Call call,  Response response) throws IOException {

                long t2 = System.currentTimeMillis();
                Log.e("TAG", "> ums-i.yanhuamedia.tv 接口响应返回时间：" + format.format(new Date(t2)));
                Log.e("TAG", "> ums-i.yanhuamedia.tv 接口耗时：" + (t2 - t3) + "ms" );
                String result = new String(response.body().bytes());
                Log.e("TAG", "> onResponse, origin:" + result );
//                if (result.contains(".")){
//                    result = result.split(Pattern.quote("."))[0];
//                }
//                Log.e("TAG", "> onResponse, after:" + result );


            }
        });

    }
    */

    private void testDate() {

        long curTime = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Log.e("TAG", "> curTimeFormat:" + format.format(1604318089357L));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(curTime);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        Log.e("TAG", "> year:" + year + ", month:" + month);

        // 下个月1号00:00:00
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long newTime = calendar.getTimeInMillis();
        // 减去1s时间，即上个月最后一天的23:59:59
        long lastDayTime = newTime - 1000;
        Log.e("TAG", "> lastDayTimeFormat:" + format.format(lastDayTime));

    }

    private void testComma() {

//        String sComma = "12345,666789";
        String sComma = "12345,";
        String encode_sComma = URLEncoder.encode(sComma);
        String decode_sComma = URLDecoder.decode(encode_sComma);
        Log.e("TAG", "> 解码后内容：" + decode_sComma);
        if (decode_sComma.contains(",")){
            String[] productIds = decode_sComma.split(",");
            Log.e("TAG", "> 个数：" + productIds.length);
            for (int i = 0;i< productIds.length;i++){
                Log.e("TAG", "> productIds[" + i + "]=" + productIds[i]);
            }
        }


    }

    private void testVolume() {
        tv_data = findViewById(R.id.tv_data);

        // 获取音量
        findViewById(R.id.btn_volume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objVolume = AudioUtil.newInstance(MainActivity.this).getCurrentVolume();
                Log.e("TAG", "获取音量 : " + objVolume);
                tv_data.setText("获取音量 : " + objVolume);
            }
        });

        // 开启静音
        findViewById(R.id.btn_silence_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objOff = AudioUtil.newInstance(MainActivity.this).audioOff();
                Log.e("TAG", "开启静音 : " + objOff);
                tv_data.setText("开启静音 : " + objOff);
            }
        });

        // 取消静音
        findViewById(R.id.btn_silence_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objOn = AudioUtil.newInstance(MainActivity.this).audioOn();
                Log.e("TAG", "取消静音 : " + objOn);
                tv_data.setText("取消静音 : " + objOn);
            }
        });

        // 设置音量
        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objSet = AudioUtil.newInstance(MainActivity.this).audioSet(62);
                Log.e("TAG", "设置音量 : " + objSet);
                tv_data.setText("设置音量 : " + objSet);
            }
        });

        // 调大音量
        findViewById(R.id.btn_up).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objUp = AudioUtil.newInstance(MainActivity.this).audioUp();
                tv_data.setText("调大音量 : " + objUp);
            }
        });

        // 调小音量
        findViewById(R.id.btn_down).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String objDown = AudioUtil.newInstance(MainActivity.this).audioDown();
                tv_data.setText("调小音量 : " + objDown);
            }
        });
    }

    private void testJson() {

        List<EquitiesBean> list = new ArrayList<>();
        list.add(new EquitiesBean("123", "202001010101", "202101010101"));
        list.add(new EquitiesBean("456", "202001010102", "202101010102"));
        list.add(new EquitiesBean("789", "202001010103", "202101010103"));

        Equitity equitity = new Equitity();
        equitity.setEquities(list);

        String s = JSON.toJSONString(equitity);
        Log.e("TAG", "> s = " + s);
        Log.e("TAG", "> list = " + JSON.toJSONString(list));

    }
}
