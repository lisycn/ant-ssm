import React from "react";
import {Row, Col, message, Button, Modal, Icon, Form,DatePicker,Select,Input} from "antd";
import {filterObject} from "../../../common/util";


const RangePicker = DatePicker.RangePicker;
const Option = Select.Option;

class JobInfoSearchForm extends  React.Component{
  constructor(props){
    super(props);
    this.state={}
  }
  onChange(value, dateString) {
    this.props.form.setFieldsValue({ "startcDate": dateString[0], "endDate": dateString[1] });
  }
  handlerDatePickerReset(){
    this.props.form.setFieldsValue({ "startcDate": "", "endDate": "" });
  }


  render(){

    //定义变量和参数
    const FormItem = Form.Item;
    const {onSubmit, onReset} = this.props;
    const {getFieldProps,getFieldValue} = this.props.form;

    const jobGroups = getFieldProps("jobGroup", {
      initialValue: "DEFAULT"
    });

    const searchDate=[];
    if(this.props.visableDate){
      searchDate.push(<FormItem label="调度时间：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }} key="search1">
        <RangePicker showTime format="yyyy-MM-dd HH:mm:ss"
                     onChange={this.onChange.bind(this)}
                     value={[getFieldValue("startcDate"), getFieldValue("endDate")]} />
      </FormItem>);
    }

    return(
      <Form horizontal className="advanced-search-form">
        <div hidden={true}>
          <FormItem>
            <Input {...getFieldProps("startcDate") } />
          </FormItem>
          <FormItem>
            <Input {...getFieldProps("endDate") } />
          </FormItem>
          <FormItem>
            <Input {...getFieldProps("jobGroups") } />
          </FormItem>
        </div>
        <Row>
          <Col sm={12} md={8}>
            <FormItem label="任务名：" labelCol={{ span: 10 }} wrapperCol={{ span: 14 }}>
              <Input placeholder="请输入任务名" {...getFieldProps("jobName")} />
            </FormItem>
          </Col>
          <Col sm={12} md={8}>
            {searchDate}
          </Col>
        </Row>
        <Row>
          <Col sm={{span: 12, offset: 12}} md={{span: 6, offset: 18}} style={{ textAlign: "right" }}>
            <Button type="primary" htmlType="submit" onClick={(e) => {
                            e.preventDefault();
                            onSubmit(filterObject(this.props.form.getFieldsValue()));
                        }}>搜索</Button>
            <Button onClick={(e) => {
                            e.preventDefault();
                            this.props.form.resetFields();
                            this.handlerDatePickerReset();
                            onReset();
                        }}>重置</Button>
          </Col>
        </Row>

      </Form>
    );
  }

}

//必须有create包装,才会带this.props.form属性
JobInfoSearchForm = Form.create()(JobInfoSearchForm);
export default JobInfoSearchForm;
