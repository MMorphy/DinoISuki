import UserDTO from "../model/UserDTO";
import axios, {AxiosResponse} from 'axios';
import ContactInformationDTO from "../model/ContactInformationDTO";

class UserRepository {

    submitRegistration(userRegistrationDto: UserDTO): Promise<AxiosResponse> {
        return axios.post("/api/user/createUser", userRegistrationDto);
    }

    login(userLoginDto: UserDTO): Promise<AxiosResponse> {
        return axios.post("/api/user/login", userLoginDto);
    }

    fetchUser(username: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/user/getUser/${username}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

    deleteUser(userDto: UserDTO) {
        axios.post("/api/user/deleteUser", userDto);
    }

    fetchUserContactInfo(username: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/user/getContactInfo/${username}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

    fetchUserProfilePhoto(username: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/user/getProfilePhoto?username=${username}`,
            {
                responseType: 'arraybuffer',
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

    submitUserUpdate(userUpdateDto: UserDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/user/updateUser", userUpdateDto,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

    saveContactInfo(contactInformationDTO: ContactInformationDTO): Promise<AxiosResponse> {
        return axios.post("/api/user/saveContactInfo", contactInformationDTO);
    }

    uploadProfilePhoto(profilePhotoFile: File, username: string, token: string): Promise<AxiosResponse> {
        const profilePhoto = new FormData();
        profilePhoto.append('profilePhoto', profilePhotoFile);

        return axios.post(`/api/user/uploadProfilePhoto?username=${username}`, profilePhoto,
            {
                headers: {
                    'content-type': 'multipart/form-data',
                    'Authorization': `Bearer ${token}`
                }
            });
    }

    updatePassword(passwordDto: any, token: string): Promise<AxiosResponse> {
        return axios.post("/api/user/changePassword", passwordDto,
            {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
    }

	getUserRoles(username: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/user/getUserRoles?username=${username}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	getAllUsers(token: string): Promise<AxiosResponse> {
        return axios.get(`/api/user/getAllUsers`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }
}

const userRepository = new UserRepository();

export default userRepository;