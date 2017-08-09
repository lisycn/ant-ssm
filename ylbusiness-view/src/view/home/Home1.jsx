import './Home.less';
import React from 'react';
import { Row, Col } from 'antd';
import TdCodeBox from '../../component/TdCodeBox';
import TdCard from '../../component/TdCard';
import TdPageTable from '../../component/TdPageTable';
import TdMulUploader from '../../component/TdMulUploader';

class Home extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
    return (
            <div>
                <h2>首页&nbsp; -&nbsp; 渠道管理平台</h2><br />
                <Row gutter={16}>
                    <Col sm={24} md={24}>
                         <Row>
                            <Col span="24">
                                <TdCard hideHead="true" shadow="true" style={{ height: 400 }}>
                                    <h3>系统说明</h3><br />
                                    <TdCodeBox title="Describe" desc="">
                                        源来科技渠道管理平台，为渠道方提供操作记录以及交易记录查询。
                                    </TdCodeBox>
                                </TdCard>
                            </Col>
                        </Row>
                    </Col>
                </Row>
            </div>
        );
  }
}
export default Home;
