package entity;

/**
 * grid has three status
 * Covered - un-clicked
 * Flag - flag
 * Clicked - clicked
 *
 * 该三种状态与格子本身内容是否为雷组合到一起，其实可以组合出五种状态：
 * 未点击、插旗子正确、插旗子错误、点到雷、点到普通格子。
 * 想直接在这里列出五种状态的话，其实也行，就看你们啦~
 */
public enum GridStatus {
    Covered,Flag,Clicked,Scanned,See;
    /*
    scanned的类型描述在进行点击空白展开全部空白时候，点击后已经展开了这个0为中心的九宫格，不能再次重复展开，防止程序陷入无限循环；
     在补充定义该状态时，采用和clicked相同的定义方法，使其也能进行普通的展开，后期如果需要对于这些状态的重新赋值，直接在draw方法下进行修改。
     */
}
