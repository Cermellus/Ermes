import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICreditRequestLine } from 'app/shared/model/credit-request-line.model';

type EntityResponseType = HttpResponse<ICreditRequestLine>;
type EntityArrayResponseType = HttpResponse<ICreditRequestLine[]>;

@Injectable({ providedIn: 'root' })
export class CreditRequestLineService {
    public resourceUrl = SERVER_API_URL + 'api/credit-request-lines';

    constructor(protected http: HttpClient) {}

    create(creditRequestLine: ICreditRequestLine): Observable<EntityResponseType> {
        return this.http.post<ICreditRequestLine>(this.resourceUrl, creditRequestLine, { observe: 'response' });
    }

    update(creditRequestLine: ICreditRequestLine): Observable<EntityResponseType> {
        return this.http.put<ICreditRequestLine>(this.resourceUrl, creditRequestLine, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICreditRequestLine>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICreditRequestLine[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
