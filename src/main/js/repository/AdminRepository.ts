import axios, {AxiosResponse} from 'axios';

class AdminRepository {

    getAdminStatistics(token: string): Promise<AxiosResponse> {
		return axios.get("/api/admin/getAdminStatistics",
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

}

const adminRepository = new AdminRepository();

export default adminRepository;