import * as React from "react";
import {observer} from "mobx-react";
import EditModal from "./EditModal";
import userStore from "../../store/UserStore";
import SecurityOfOnlinePaymentsModal from "./SecurityOfOnlinePaymentsModal";
import GeneralInformationCard from "./GeneralInformationCard";
import UserSubscriptionCard from "./UserSubscriptionCard";
import CorvusAndPaymentLogos from "../utils/CorvusAndPaymentLogos";
import appStore from "../../store/AppStore";
import {Button} from "react-bootstrap";
import ChangePasswordModal from "./ChangePasswordModal";
import UserDTO from "../../model/UserDTO";
import {action} from "mobx";
import ContactInformationDTO from "../../model/ContactInformationDTO";

@observer
export default class MyProfile extends React.Component<{}, {}> {
    render() {
        document.title = "MyProfile";

        return (
            <div className="my-profile-container">
                <EditModal/>
                <ChangePasswordModal/>
                <SecurityOfOnlinePaymentsModal/>
                <GeneralInformationCard/>
                <UserSubscriptionCard/>
                <br/>
                <Button variant="link" onClick={() => appStore.changeSecurityOfOnlinePaymentsModalVisibility()}>Security of Online Payments</Button>
                <CorvusAndPaymentLogos/>
            </div>
        );
    }

    componentDidMount(): void {
        if (sessionStorage.getItem('username')) {
            userStore.fetchUserProfile();
            userStore.fetchUserProfilePhoto();
        }
    }

    @action
    componentWillUnmount(): void {
        userStore.userProfileDto = new UserDTO();
        userStore.userUpdateDto = new UserDTO();
        userStore.contactInformationProfileDto = new ContactInformationDTO();
        userStore.contactInformationUpdateDto = new ContactInformationDTO();
    }
}