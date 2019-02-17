export class ConfigService {
    private serviceUrl: string;

    constructor(){
        this.serviceUrl = 'http://localhost:8090/api/';
    }

    getServiceUrl(): string {
        return this.serviceUrl;
    }
}