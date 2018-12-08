import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ICmRequest } from 'app/shared/model/cm-request.model';
import { CmRequestService } from './cm-request.service';

@Component({
    selector: 'jhi-cm-request-update',
    templateUrl: './cm-request-update.component.html'
})
export class CmRequestUpdateComponent implements OnInit {
    cmRequest: ICmRequest;
    isSaving: boolean;
    startDateTime: string;
    endDateTime: string;

    constructor(private cmRequestService: CmRequestService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cmRequest }) => {
            this.cmRequest = cmRequest;
            this.startDateTime = this.cmRequest.startDateTime != null ? this.cmRequest.startDateTime.format(DATE_TIME_FORMAT) : null;
            this.endDateTime = this.cmRequest.endDateTime != null ? this.cmRequest.endDateTime.format(DATE_TIME_FORMAT) : null;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.cmRequest.startDateTime = this.startDateTime != null ? moment(this.startDateTime, DATE_TIME_FORMAT) : null;
        this.cmRequest.endDateTime = this.endDateTime != null ? moment(this.endDateTime, DATE_TIME_FORMAT) : null;
        if (this.cmRequest.id !== undefined) {
            this.subscribeToSaveResponse(this.cmRequestService.update(this.cmRequest));
        } else {
            this.subscribeToSaveResponse(this.cmRequestService.create(this.cmRequest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICmRequest>>) {
        result.subscribe((res: HttpResponse<ICmRequest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
