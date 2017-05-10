package com.haokan.onescreen.utils;

import java.util.ArrayList;
import java.util.List;

import com.haokan.onescreen.model.ListBean;
import com.haokan.onescreen.model.SmallsBean;

public class DefaultDataUtils {
	
	
	public static ListBean getDefaultData(){
		
		
		ListBean listbean = new ListBean();
		
		listbean.setTitle("啤酒排骨");
		listbean.setContent("啤酒排骨,过程,步骤,烹饪,美食");
		listbean.setImageId(379724);
		listbean.setImageUrl("http://res.levect.com/hkimages/35/39/148826515864594503935.jpg@!360x640?k=10047&pid=10047&eid=102022");
		listbean.setImgClickUrl("http://levect.com/zh/detail/tag/46/img/379724/simg/379724/?eid=102022&appid=10047&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216");
		listbean.setImageTpye("2");
		listbean.setNativeUrl("haokanyitu://photos/?eid=102022&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216&id=379724&index=1");
		
		
		List<SmallsBean> Smalllist = new ArrayList<SmallsBean>();
		
		SmallsBean smallsBean = new SmallsBean();
		smallsBean.setImageId(379725);
		smallsBean.setImageUrl("http://res.levect.com/hkimages/93/28/148826517761297502893.jpg@!360x640?k=10047&pid=10047&eid=102022");
		smallsBean.setImgClickUrl("http://levect.com/zh/detail/tag/46/img/379724/simg/379725/?eid=102022&appid=10047&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216");
		smallsBean.setNativeUrl("haokanyitu://photos/?eid=102022&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216&id=379724&index=2");
		
		Smalllist.add(smallsBean);
		
		SmallsBean smallsBean1 = new SmallsBean();
		smallsBean1.setImageId(379726);
		smallsBean1.setImageUrl("http://res.levect.com/hkimages/56/35/148826519328999663556.jpg@!360x640?k=10047&pid=10047&eid=102022");
		smallsBean1.setImgClickUrl("http://levect.com/zh/detail/tag/46/img/379724/simg/379726/?eid=102022&appid=10047&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216");
		smallsBean1.setNativeUrl("haokanyitu://photos/?eid=102022&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216&id=379724&index=3");
		
		Smalllist.add(smallsBean1);
		
		SmallsBean smallsBean2 = new SmallsBean();
		smallsBean2.setImageId(379727);
		smallsBean2.setImageUrl("http://res.levect.com/hkimages/23/49/148826521140094994923.jpg@!360x640?k=10047&pid=10047&eid=102022");
		smallsBean2.setImgClickUrl("http://levect.com/zh/detail/tag/46/img/379724/simg/379727/?eid=102022&appid=10047&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216");
		smallsBean2.setNativeUrl("haokanyitu://photos/?eid=102022&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216&id=379724&index=4");
		
		Smalllist.add(smallsBean2);
		
		SmallsBean smallsBean3 = new SmallsBean();
		smallsBean3.setImageId(379728);
		smallsBean3.setImageUrl("http://res.levect.com/hkimages/89/88/148826526104599088889.jpg@!360x640?k=10047&pid=10047&eid=102022");
		smallsBean3.setImgClickUrl("http://levect.com/zh/detail/tag/46/img/379724/simg/379728/?eid=102022&appid=10047&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216");
		smallsBean3.setNativeUrl("haokanyitu://photos/?eid=102022&did=4E7A17ECB740E9F3EDC7BFA9EFCB9216&id=379724&index=5");
		Smalllist.add(smallsBean3);
		
		
		listbean.setSmalls(Smalllist);
		
		return listbean;
	}
}
