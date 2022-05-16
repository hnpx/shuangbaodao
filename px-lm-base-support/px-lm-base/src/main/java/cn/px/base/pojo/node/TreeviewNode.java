/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.px.base.pojo.node;

import cn.stylefeng.roses.kernel.model.enums.YesOrNotEnum;
import cn.stylefeng.roses.kernel.model.tree.Tree;
import lombok.Data;

import java.util.*;

/**
 * jquery ztree 插件的节点
 *
 * @author fengshuonan
 * @date 2017年2月17日 下午8:25:14
 */
@Data
public class TreeviewNode implements Tree,Comparable {

    /**
     * 附加信息，一般用于存业务id
     */
    private String tags;

    /**
     * 父级id
     */
    private String parentId;

    /**
     * 节点名称
     */
    private String text;
    private Integer levels;
    private Integer num;
    /**
     * 子节点
     */
    private List<TreeviewNode> nodes;

    @Override
    public String getNodeId() {
        return tags;
    }

    @Override
    public String getNodeParentId() {
        return parentId;
    }

    @Override
    public void setChildrenNodes(List childrenNodes) {
        this.nodes = childrenNodes;
    }

    @Override
    public int compareTo(Object o) {
        TreeviewNode menuNode = (TreeviewNode) o;
        Integer num = menuNode.getNum();
        Integer levels = menuNode.getLevels();
        if (num == null) {
            num = 0;
        }
        if (levels == null) {
            levels = 0;
        }
        if (this.levels.compareTo(levels) == 0) {
            return this.num.compareTo(num);
        } else {
            return this.levels.compareTo(levels);
        }

    }
    public static List<TreeviewNode> buildTitle(List<TreeviewNode> nodes) {
        if (nodes.size() <= 0) {
            return nodes;
        }

        Collections.sort(nodes);
        List<TreeviewNode> result=mergeList(nodes, nodes.get(nodes.size() - 1).getLevels(), null);
        return result;
    }

    /**
     * 递归合并数组为子数组，最后返回第一层
     *
     * @param menuList
     * @param listMap
     * @return
     */
    private static List<TreeviewNode> mergeList(List<TreeviewNode> menuList, int rank, Map<String , List<TreeviewNode>> listMap) {
        //保存当次调用总共合并了多少元素
        int n;
        //保存当次调用总共合并出来的list
        Map<String , List<TreeviewNode>> currentMap = new HashMap<>();
        //由于按等级从小到大排序，需要从后往前排序
        //判断该节点是否属于当前循环的等级,不等于则跳出循环
        for (n = menuList.size() - 1; n >= 0 && menuList.get(n).getLevels() == rank; n--) {
            //判断之前的调用是否有返回以该节点的id为key的map，有则设置为children列表。
            if (listMap != null && listMap.get(menuList.get(n).getNodeId()) != null) {
                menuList.get(n).setChildrenNodes(listMap.get(menuList.get(n).getNodeId()));
            }
            if (menuList.get(n).getParentId() != null && !menuList.get(n).getNodeParentId().equals("0")) {
                //判断当前节点所属的pid是否已经创建了以该pid为key的键值对，没有则创建新的链表
                currentMap.computeIfAbsent(menuList.get(n).getNodeParentId(), k -> new LinkedList<>());
                //将该节点插入到对应的list的头部
                currentMap.get(menuList.get(n).getParentId()).add(0, menuList.get(n));
            }
        }
        if (n < 0) {
            return menuList;
        } else {
            return mergeList(new ArrayList<>(menuList.subList(0, n + 1)), menuList.get(n).getLevels(), currentMap);
        }
    }
}
