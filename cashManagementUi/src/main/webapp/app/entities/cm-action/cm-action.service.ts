import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICmAction } from 'app/shared/model/cm-action.model';

type EntityResponseType = HttpResponse<ICmAction>;
type EntityArrayResponseType = HttpResponse<ICmAction[]>;

@Injectable({ providedIn: 'root' })
export class CmActionService {
    public resourceUrl = SERVER_API_URL + 'api/cm-actions';

    constructor(private http: HttpClient) {}

    create(cmAction: ICmAction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmAction);
        return this.http
            .post<ICmAction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(cmAction: ICmAction): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(cmAction);
        return this.http
            .put<ICmAction>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICmAction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICmAction[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(cmAction: ICmAction): ICmAction {
        const copy: ICmAction = Object.assign({}, cmAction, {
            actionDateTime: cmAction.actionDateTime != null && cmAction.actionDateTime.isValid() ? cmAction.actionDateTime.toJSON() : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.actionDateTime = res.body.actionDateTime != null ? moment(res.body.actionDateTime) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((cmAction: ICmAction) => {
                cmAction.actionDateTime = cmAction.actionDateTime != null ? moment(cmAction.actionDateTime) : null;
            });
        }
        return res;
    }
}
