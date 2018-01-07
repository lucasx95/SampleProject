import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SampleMany } from './sample-many.model';
import { SampleManyService } from './sample-many.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-sample-many',
    templateUrl: './sample-many.component.html'
})
export class SampleManyComponent implements OnInit, OnDestroy {
sampleManies: SampleMany[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private sampleManyService: SampleManyService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.sampleManyService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.sampleManies = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.sampleManyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sampleManies = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSampleManies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SampleMany) {
        return item.id;
    }
    registerChangeInSampleManies() {
        this.eventSubscriber = this.eventManager.subscribe('sampleManyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
