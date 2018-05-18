package com.ytcadmin.mail.intf;

import com.ytcadmin.common.model.EmailDetails;

public interface IYTMMMailConnectorService {
	void sendEmail(EmailDetails emailDetails);
}
