package org.ys.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CoreDeptExample implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -549331276294504465L;

	/**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public CoreDeptExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria implements Serializable{
        /**
		 * 
		 */
		private static final long serialVersionUID = 3061672359165930018L;
		protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andCoreDeptIdIsNull() {
            addCriterion("core_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdIsNotNull() {
            addCriterion("core_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdEqualTo(Long value) {
            addCriterion("core_dept_id =", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdNotEqualTo(Long value) {
            addCriterion("core_dept_id <>", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdGreaterThan(Long value) {
            addCriterion("core_dept_id >", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("core_dept_id >=", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdLessThan(Long value) {
            addCriterion("core_dept_id <", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("core_dept_id <=", value, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdIn(List<Long> values) {
            addCriterion("core_dept_id in", values, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdNotIn(List<Long> values) {
            addCriterion("core_dept_id not in", values, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdBetween(Long value1, Long value2) {
            addCriterion("core_dept_id between", value1, value2, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andCoreDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("core_dept_id not between", value1, value2, "coreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdIsNull() {
            addCriterion("parent_core_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdIsNotNull() {
            addCriterion("parent_core_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdEqualTo(Long value) {
            addCriterion("parent_core_dept_id =", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdNotEqualTo(Long value) {
            addCriterion("parent_core_dept_id <>", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdGreaterThan(Long value) {
            addCriterion("parent_core_dept_id >", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_core_dept_id >=", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdLessThan(Long value) {
            addCriterion("parent_core_dept_id <", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_core_dept_id <=", value, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdIn(List<Long> values) {
            addCriterion("parent_core_dept_id in", values, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdNotIn(List<Long> values) {
            addCriterion("parent_core_dept_id not in", values, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdBetween(Long value1, Long value2) {
            addCriterion("parent_core_dept_id between", value1, value2, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andParentCoreDeptIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_core_dept_id not between", value1, value2, "parentCoreDeptId");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNull() {
            addCriterion("dept_code is null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIsNotNull() {
            addCriterion("dept_code is not null");
            return (Criteria) this;
        }

        public Criteria andDeptCodeEqualTo(String value) {
            addCriterion("dept_code =", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotEqualTo(String value) {
            addCriterion("dept_code <>", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThan(String value) {
            addCriterion("dept_code >", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeGreaterThanOrEqualTo(String value) {
            addCriterion("dept_code >=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThan(String value) {
            addCriterion("dept_code <", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLessThanOrEqualTo(String value) {
            addCriterion("dept_code <=", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeLike(String value) {
            addCriterion("dept_code like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotLike(String value) {
            addCriterion("dept_code not like", value, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeIn(List<String> values) {
            addCriterion("dept_code in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotIn(List<String> values) {
            addCriterion("dept_code not in", values, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeBetween(String value1, String value2) {
            addCriterion("dept_code between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptCodeNotBetween(String value1, String value2) {
            addCriterion("dept_code not between", value1, value2, "deptCode");
            return (Criteria) this;
        }

        public Criteria andDeptDescIsNull() {
            addCriterion("dept_desc is null");
            return (Criteria) this;
        }

        public Criteria andDeptDescIsNotNull() {
            addCriterion("dept_desc is not null");
            return (Criteria) this;
        }

        public Criteria andDeptDescEqualTo(String value) {
            addCriterion("dept_desc =", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescNotEqualTo(String value) {
            addCriterion("dept_desc <>", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescGreaterThan(String value) {
            addCriterion("dept_desc >", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescGreaterThanOrEqualTo(String value) {
            addCriterion("dept_desc >=", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescLessThan(String value) {
            addCriterion("dept_desc <", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescLessThanOrEqualTo(String value) {
            addCriterion("dept_desc <=", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescLike(String value) {
            addCriterion("dept_desc like", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescNotLike(String value) {
            addCriterion("dept_desc not like", value, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescIn(List<String> values) {
            addCriterion("dept_desc in", values, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescNotIn(List<String> values) {
            addCriterion("dept_desc not in", values, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescBetween(String value1, String value2) {
            addCriterion("dept_desc between", value1, value2, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andDeptDescNotBetween(String value1, String value2) {
            addCriterion("dept_desc not between", value1, value2, "deptDesc");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(Integer value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(Integer value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(Integer value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(Integer value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(Integer value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<Integer> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<Integer> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(Integer value1, Integer value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(Integer value1, Integer value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNull() {
            addCriterion("del_flag is null");
            return (Criteria) this;
        }

        public Criteria andDelFlagIsNotNull() {
            addCriterion("del_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDelFlagEqualTo(Integer value) {
            addCriterion("del_flag =", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotEqualTo(Integer value) {
            addCriterion("del_flag <>", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThan(Integer value) {
            addCriterion("del_flag >", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_flag >=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThan(Integer value) {
            addCriterion("del_flag <", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagLessThanOrEqualTo(Integer value) {
            addCriterion("del_flag <=", value, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagIn(List<Integer> values) {
            addCriterion("del_flag in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotIn(List<Integer> values) {
            addCriterion("del_flag not in", values, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagBetween(Integer value1, Integer value2) {
            addCriterion("del_flag between", value1, value2, "delFlag");
            return (Criteria) this;
        }

        public Criteria andDelFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("del_flag not between", value1, value2, "delFlag");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core_dept
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        /**
		 * 
		 */
		private static final long serialVersionUID = -8727579124077991826L;

		protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table core_dept
     *
     * @mbg.generated
     */
    public static class Criterion implements Serializable{
        /**
		 * 
		 */
		private static final long serialVersionUID = -2421411574477694966L;

		private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}