import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditRequest } from 'app/shared/model/credit-request.model';

type EntityResponseType = HttpResponse<ICreditRequest>;
type EntityArrayResponseType = HttpResponse<ICreditRequest[]>;

@Injectable({ providedIn: 'root' })
export class CreditRequestService {
    public resourceUrl = SERVER_API_URL + 'api/credit-requests';

    constructor(protected http: HttpClient) {}

    create(creditRequest: ICreditRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(creditRequest);
        return this.http
            .post<ICreditRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(creditRequest: ICreditRequest): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(creditRequest);
        return this.http
            .put<ICreditRequest>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICreditRequest>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICreditRequest[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(creditRequest: ICreditRequest): ICreditRequest {
        const copy: ICreditRequest = Object.assign({}, creditRequest, {
            creditRequestDate:
                creditRequest.creditRequestDate != null && creditRequest.creditRequestDate.isValid()
                    ? creditRequest.creditRequestDate.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.creditRequestDate = res.body.creditRequestDate != null ? moment(res.body.creditRequestDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((creditRequest: ICreditRequest) => {
                creditRequest.creditRequestDate = creditRequest.creditRequestDate != null ? moment(creditRequest.creditRequestDate) : null;
            });
        }
        return res;
    }
}
