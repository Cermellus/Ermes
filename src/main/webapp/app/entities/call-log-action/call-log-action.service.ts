import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICallLogAction } from 'app/shared/model/call-log-action.model';

type EntityResponseType = HttpResponse<ICallLogAction>;
type EntityArrayResponseType = HttpResponse<ICallLogAction[]>;

@Injectable({ providedIn: 'root' })
export class CallLogActionService {
    public resourceUrl = SERVER_API_URL + 'api/call-log-actions';

    constructor(protected http: HttpClient) {}

    create(callLogAction: ICallLogAction): Observable<EntityResponseType> {
        return this.http.post<ICallLogAction>(this.resourceUrl, callLogAction, { observe: 'response' });
    }

    update(callLogAction: ICallLogAction): Observable<EntityResponseType> {
        return this.http.put<ICallLogAction>(this.resourceUrl, callLogAction, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ICallLogAction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ICallLogAction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
