import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { VoteItem } from './vote-item.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VoteItemService {

    private resourceUrl =  SERVER_API_URL + 'api/vote-items';

    constructor(private http: Http) { }

    create(voteItem: VoteItem): Observable<VoteItem> {
        const copy = this.convert(voteItem);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(voteItem: VoteItem): Observable<VoteItem> {
        const copy = this.convert(voteItem);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<VoteItem> {
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
     * Convert a returned JSON object to VoteItem.
     */
    private convertItemFromServer(json: any): VoteItem {
        const entity: VoteItem = Object.assign(new VoteItem(), json);
        return entity;
    }

    /**
     * Convert a VoteItem to a JSON which can be sent to the server.
     */
    private convert(voteItem: VoteItem): VoteItem {
        const copy: VoteItem = Object.assign({}, voteItem);
        return copy;
    }
}
