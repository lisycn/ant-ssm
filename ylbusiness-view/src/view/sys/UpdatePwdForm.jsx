import React from 'react';
import {Row, Col, Form, Input, Select, Button, Checkbox, Icon, InputNumber, Radio ,TreeSelect,Tree } from 'antd';
import TdCard from "../../component/TdCard";
import { callAjax } from '../../common/util';
import { ylagent } from "../../config/server";
import { rspInfo } from "../../common/authConstant";
import { openNotice } from "../../common/antdUtil";
const FormItem = Form.Item;
const RadioGroup = Radio.Group;
const TreeNode = Tree.TreeNode;

/**
 * 更改密码
 * 
 * Auth:li.sy  Time:2016-07-04
 */
class UpdatePwdForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            valid: false,
            value: "",
            treeData:[]
        }
    }
    
    //父页面通过修改props 中属性的值触发该方法
    componentWillReceiveProps(nextProps) {
        //更新表单数据
        if (nextProps.valid === true && this.state.valid === false) {
            this.setState({ valid: true }, () => {
                this.validForm();
                this.setState({ valid: false });
            });
        }
        console.log("formReset = "  + nextProps.formReset);    
        //重置表单
        if (nextProps.formReset === true) {
            console.log("child component form reset.");
            this.props.form.resetFields();
            this.setState({
                value:""
            });
        }
    }
    
    //子页面表单校验
    validForm() {
        console.log("=====进入validForm");
        const { oprType, validCallback } = this.props;
        const obj = this;
        this.props.form.validateFields((errors, data) => {
            //data.orgId = this.state.value;
            console.log("error = "+errors);
            //执行父页面回调函数
            validCallback(oprType, errors, data);
        });
    }
    onChange(value) {
        console.log(arguments);
        this.setState({ value });
    }
    handleSubmit(){
        openNotice("info","想改密码，联系陈义强，5块钱一次");
    }
    handleReset(){
        this.props.form.resetFields();
    }
    /**
     * 校验2次密码一致
     */
    checkPass2(rule, value, callback) {
        const { getFieldValue } = this.props.form;
        if (value && value !== getFieldValue('newPwd')) {
            callback('两次输入新密码不一致！');
        } else {
            callback();
        }
    }
    render() {
        const formItemLayout = {
            labelCol: { span: 6 },
            wrapperCol: { span: 14 }
        };

        const { idObj, oprType, formData } = this.props;

        const { getFieldProps, isFieldValidating, getFieldError } = this.props.form;
        
        const itemDisable = {
            disabled: true
        }
        const itemHidden = {
            hidden: true
        }
        //表单校验
        const newPwd = getFieldProps("newPwd", {
            initialValue: '',
            validate: [{
                rules: [
                    { required: true, message: '新密码不能为空',whitespace: true },
                    { max:10,message:'密码最大10位'}
                ],
                trigger: 'onBlur'
            }],
            validateFirst:true
        });
        const newPwdRepeat = getFieldProps("newPwdRepeat", {
            initialValue: '',
            validate: [{
                rules: [
                    { required: true, message: '确认密码不能为空',whitespace: true },
                    { max:10,message:'密码最大10位'},
                    { validator: this.checkPass2.bind(this)}
                ],
                trigger: 'onBlur'
                
            }],
            validateFirst:true
        });
        return (
            <Form className="compact-form" horizontal form={this.props.form}>
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem label="请输入新密码：" {...formItemLayout}>
                            <Input type="password" placeholder="请输入新密码" maxLength="10" {...newPwd} />
                        </FormItem>
                    </Col>
                </Row>
                
                <Row>
                    <Col sm={24} md={24}>
                        <FormItem  label="请再次输入新密码：" {...formItemLayout}>
                            <Input type="password" placeholder="请再次输入新密码"  maxLength="10"  {...newPwdRepeat} />
                        </FormItem>
                    </Col>
                </Row>
               
            </Form>
        );
    }
}

const noop = () => { };
UpdatePwdForm.defaultProps = {
    valid: false,        //校验状态，通过父页面修改该值触发componentWillReceiveProps方法
    idObj: {},          //ID属性对象
    oprType: 0,         //操作类型 0：默认值 1：新增 2：修改 3详情：
    formData: {},        //父页面表单数据
    validCallback: noop, //回调函数
    formReset: false,     //表单重置标识位
    treeData:[]
}

UpdatePwdForm = Form.create()(UpdatePwdForm);
export default UpdatePwdForm;