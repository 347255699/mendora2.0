package org.mendora.util.constant;

/**
 * created by:xmf
 * date:2017/10/31
 * description:
 */
public interface MongoSymbol {
    /**
     * {"$inc": {"field1": amount1, "field2": amount2, ....}}
     * 将文档中的某个field对应的value自增/减某个数字amount
     */
    String $inc = "$inc";

    /**
     * {"$set": { "field1": "val", "field2": "val2", ...}}
     * 更新文档中的某一个字段，而不是全部替换
     */
    String $set = "$set";

    /**
     * {"$unset": { "field1": "", "field2": "", ...}}
     * 删除文档中的指定字段，若字段不存在则不操作
     */
    String $unset = "$unset";

    /**
     * {"adapter": {"$in": ["val", "val", ...]}}
     * 匹配数组中的任一值
     */
    String $in = "$in";

    /**
     * {"adapter": {"$nin": ["val", "val", ...]}}
     * 不匹配数组中的值
     */
    String $nin = "$nin";

    /**
     * {"$or": [ {"field1": "val"}, {"field2": "val2"}, ... ] }
     * 或 条件查询
     */
    String $or = "$or";

    /**
     * {"adapter": {"$gt": val }}
     * 匹配大于（>）指定值的文档
     */
    String $gt = "$gt";

    /**
     * {"adapter": {"$gte": val }}
     * 匹配大于等于（>=）指定值的文档
     */
    String $gte = "$gte";

    /**
     * {"adapter": {"$lt": val }}
     * 匹配小于（<）指定值的文档
     */
    String $lt = "$lt";

    /**
     * {"adapter": {"$lte": val }}
     * 匹配小于等于（<=）指定值的文档
     */
    String $lte = "$lte";

    /**
     * {"adapter": {"$eq": val }}
     * 匹配等于（=）指定值的文档
     */
    String $eq = "$eq";

    /**
     * {"adapter": {"$ne": val }}
     * 匹配不等于（≠）指定值的文档
     */
    String $ne = "$ne";

    /**
     * {"$and": [ expression1, expression2, ...]}
     * 与 条件查询
     */
    String $and = "$and";

    /**
     * {"adapter": {"$regex": "pattern"}}
     */
    String $regex = "$regex";

    /**
     * {"adapter": {"$all": ["val", "val2", ...]}}
     * 匹配文档的数组字段中包含所有指定元素的文档
     */
    String $all = "$all";

    /**
     * { <adapter>: { $elemMatch: { <query1>, <query2>, ... } } }
     * 查询条件： 匹配内嵌文档或数组中的部分field
     * 过滤投影： 只返回满足$elemMatch条件的值
     */
    String $elemMatch = "$elemMatch";

    /**
     * 聚合函数
     */
    String $group = "$group";

    /**
     * 聚合函数
     */
    String $match = "$match";

    /**
     * 聚合函数
     */
    String $project = "$project";

    /**
     * 聚合
     */
    String Aggregate = "aggregate";

    String pipeline = "pipeline";
    /**
     * 是否存在
     */
    String $exists = "$exists";

    /**
     *数组添加并保证元素唯一
     */
    String $addToSet = "$addToSet";

    /**
     *数组添加并保证元素唯一
     */
    String $each = "$each";

    /**
     * 聚合操作符sum
     */
    String $sum = "$sum";

    String $push = "$push";

    /**
     * 删除数组
     */
    String $pull = "$pull";
}
