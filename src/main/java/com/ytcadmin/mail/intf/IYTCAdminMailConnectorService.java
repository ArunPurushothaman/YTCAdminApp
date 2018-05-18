package com.ytcadmin.mail.intf;

import com.ytcadmin.common.model.EmailDetails;

public interface IYTCAdminMailConnectorService {
	void sendEmail(EmailDetails emailDetails);
}
