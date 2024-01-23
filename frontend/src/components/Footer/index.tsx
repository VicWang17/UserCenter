import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
import React from 'react';

const Footer: React.FC = () => {
  return (
    <DefaultFooter
      style={{
        background: 'none',
      }}
      links={[
        {
          key: 'Personal Blog',
          title: '个人博客',
          href: 'http://vicwang17.com',
          blankTarget: true,   //是否打开新页面
        },
        {
          key: 'Github',
          title: <><GithubOutlined /> Github</>,
          href: 'https://github.com/VicWang17/UserCenter',
          blankTarget: true,
        },
        
      ]}
    />
  );
};

export default Footer;
