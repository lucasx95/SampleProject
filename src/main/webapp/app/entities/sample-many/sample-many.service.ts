import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { SampleMany } from './sample-many.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class SampleManyService {

    private resourceUrl =  SERVER_API_URL + 'api/sample-manies';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/sample-manies';

    constructor(private http: Http) { }

    create(sampleMany: SampleMany): Observable<SampleMany> {
        const copy = this.convert(sampleMany);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(sampleMany: SampleMany): Observable<SampleMany> {
        const copy = this.convert(sampleMany);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<SampleMany> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to SampleMany.
     */
    private convertItemFromServer(json: any): SampleMany {
        const entity: SampleMany = Object.assign(new SampleMany(), json);
        return entity;
    }

    /**
     * Convert a SampleMany to a JSON which can be sent to the server.
     */
    private convert(sampleMany: SampleMany): SampleMany {
        const copy: SampleMany = Object.assign({}, sampleMany);
        return copy;
    }
}
