import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Vote } from './vote.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VoteService {

    private resourceUrl =  SERVER_API_URL + 'api/votes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(vote: Vote): Observable<Vote> {
        const copy = this.convert(vote);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(vote: Vote): Observable<Vote> {
        const copy = this.convert(vote);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Vote> {
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

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Vote.
     */
    private convertItemFromServer(json: any): Vote {
        const entity: Vote = Object.assign(new Vote(), json);
        entity.startDate = this.dateUtils
            .convertDateTimeFromServer(json.startDate);
        entity.endDate = this.dateUtils
            .convertDateTimeFromServer(json.endDate);
        return entity;
    }

    /**
     * Convert a Vote to a JSON which can be sent to the server.
     */
    private convert(vote: Vote): Vote {
        const copy: Vote = Object.assign({}, vote);

        copy.startDate = this.dateUtils.toDate(vote.startDate);

        copy.endDate = this.dateUtils.toDate(vote.endDate);
        return copy;
    }
}
