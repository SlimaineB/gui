import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { ICmRequest } from 'app/shared/model/cm-request.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { CmRequestService } from './cm-request.service';
import { NgbTimeStruct, NgbCalendar, NgbDateStruct, NgbDate } from '@ng-bootstrap/ng-bootstrap';

@Component({
    selector: 'jhi-cm-request',
    templateUrl: './cm-request.component.html'
})
export class CmRequestComponent implements OnInit, OnDestroy {
    currentAccount: any;
    cmRequests: ICmRequest[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    startTime: NgbTimeStruct;
    endTime: NgbTimeStruct;
    startDate: NgbDateStruct;
    endDate: NgbDateStruct;
    spinners = false;
    model: NgbDateStruct;
    date: { year: number; month: number };

    constructor(
        private cmRequestService: CmRequestService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager,
        private calendar: NgbCalendar
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    toggleSpinners() {
        this.spinners = !this.spinners;
    }
    selectToday() {
        this.model = this.calendar.getToday();
    }

    loadAll() {
        this.cmRequestService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                sort: this.sort()
            })
            .subscribe(
                (res: HttpResponse<ICmRequest[]>) => this.paginateCmRequests(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/cm-request'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/cm-request',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.startTime = { hour: 0, minute: 0, second: 0 };
        this.endTime = { hour: 23, minute: 59, second: 59 };
        this.startDate = this.calendar.getToday();
        this.endDate = this.calendar.getToday();
        console.log(this.startDate);
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInCmRequests();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICmRequest) {
        return item.id;
    }

    registerChangeInCmRequests() {
        this.eventSubscriber = this.eventManager.subscribe('cmRequestListModification', response => this.loadAll());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private paginateCmRequests(data: ICmRequest[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.cmRequests = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
